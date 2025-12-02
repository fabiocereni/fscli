package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.FSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLsiCommandBusinessTest {

    private FSLsCommandBusiness lsCommand;
    private IFSStateBusiness state;
    private FileSystem fs;
    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {
        // Uso Guice per creare l'ambiente reale
        Injector injector = Guice.createInjector(new FileSystemModule());

        lsCommand = injector.getInstance(FSLsCommandBusiness.class);
        state = injector.getInstance(IFSStateBusiness.class);
        fs = injector.getInstance(FileSystem.class);
        IFSCreationBusiness creation = injector.getInstance(IFSCreationBusiness.class);

        creation.newfs(); // Resetta e inizializza
        root = state.getRoot();

        // Setup struttura:
        // /
        // ├── documents/ (Directory)
        // └── photo.jpg (File)

        DirectoryInodeBusiness docs = fs.createDirectory(root);
        root.addEntry("documents", docs);

        FileInodeBusiness file = fs.createFile();
        root.addEntry("photo.jpg", file);
    }

    @Test
    void testLsCurrentDirectorySimple() {
        // ls su root
        String output = lsCommand.ls(null, false);

        assertTrue(output.contains("documents"));
        assertTrue(output.contains("photo.jpg"));
        assertFalse(output.contains("["), "Non deve mostrare ID se non richiesto");
    }

    @Test
    void testLsCurrentDirectoryWithInode() {
        // ls -i su root
        String output = lsCommand.ls(null, true);

        assertTrue(output.contains("documents ["));
        assertTrue(output.contains("photo.jpg ["));
    }

    @Test
    void testLsSpecificPath() {
        // ls /documents
        String output = lsCommand.ls("/documents", false);
        // documents è vuota
        assertEquals("", output);

        // Aggiungo file dentro documents
        DirectoryInodeBusiness docs = (DirectoryInodeBusiness) root.getEntry("documents");
        docs.addEntry("notes.txt", fs.createFile());

        output = lsCommand.ls("documents", false); // path relativo
        assertTrue(output.contains("notes.txt"));
    }

    @Test
    void testLsInvalidPath() {
        String output = lsCommand.ls("non_esiste", false);
        assertTrue(output.startsWith("ls: cannot access"));
    }

    @Test
    void testLsOnFile() {
        // ls photo.jpg
        String output = lsCommand.ls("photo.jpg", false);
        assertEquals("photo.jpg", output);

        // ls -i photo.jpg
        output = lsCommand.ls("photo.jpg", true);
        assertTrue(output.startsWith("photo.jpg ["));
    }
}