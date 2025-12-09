package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.FSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSLsCommandBusinessTest {

    private FSLsCommandBusiness lsCommand;
    private FileSystem fs;
    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new FileSystemModule());
        lsCommand = injector.getInstance(FSLsCommandBusiness.class);
        fs = injector.getInstance(FileSystem.class);

        injector.getInstance(IFSCreationBusiness.class).newfs();
        root = fs.getRoot();

        fs.setCurrentWorkingDirectory(root);
    }

    @Test
    void testLsCurrentDirectoryEmpty() {
        // La root è inizialmente vuota nel setup standard (o quasi, dipende da newfs)
        // Se newfs crea root vuota:
        CommandResult result = lsCommand.ls(null, false);

        // Il tuo codice ritorna null se la lista è vuota
        assertNull(result, "Se la directory è vuota, ls ritorna null");
    }

    @Test
    void testLsCurrentDirectoryWithContent() {
        // Aggiungo file
        root.addEntry("fileA", fs.createFile());
        root.addEntry("dirB", fs.createDirectory(root));

        // ls normale
        CommandResult result = lsCommand.ls(null, false);

        assertNotNull(result);
        assertFalse(result.isTranslatable()); // Deve essere false per i dati

        String content = result.getContent();
        assertTrue(content.contains("fileA"));
        assertTrue(content.contains("dirB"));
        assertFalse(content.contains("["), "Senza -i non mostra gli ID");
    }

    @Test
    void testLsWithInodeOption() {
        root.addEntry("testFile", fs.createFile());

        // ls -i
        CommandResult result = lsCommand.ls(null, true);

        assertNotNull(result);
        assertTrue(result.getContent().contains("testFile ["));
    }

    @Test
    void testLsSpecificPath() {
        DirectoryInodeBusiness subDir = fs.createDirectory(root);
        root.addEntry("documents", subDir);
        subDir.addEntry("notes.txt", fs.createFile());

        // ls documents
        CommandResult result = lsCommand.ls("documents", false);

        assertNotNull(result);
        assertFalse(result.isTranslatable());
        assertTrue(result.getContent().contains("notes.txt"));
    }

    @Test
    void testLsPathNotFound() {
        // ls nonEsiste
        CommandResult result = lsCommand.ls("nonEsiste", false);

        assertNotNull(result);
        assertTrue(result.isTranslatable(), "Deve essere true per le label di errore");
        assertEquals("label.wrongLsUse2", result.getContent());
    }

    @Test
    void testLsOnFile() {
        root.addEntry("image.png", fs.createFile());

        // ls image.png
        CommandResult result = lsCommand.ls("image.png", false);

        assertNotNull(result);
        assertFalse(result.isTranslatable());
        assertEquals("image.png", result.getContent());
    }
}