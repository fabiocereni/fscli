package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.IFSLnCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.MyFileNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLnCommandBusinessTest {

    private Injector injector;
    private IFSLnCommandBusiness ln;

    private FileSystem fs;
    private IFSStateBusiness state;

    private DirectoryInodeBusiness root;
    private FileInodeBusiness fileA;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        ln = injector.getInstance(IFSLnCommandBusiness.class);
        fs = injector.getInstance(FileSystem.class);
        state = injector.getInstance(IFSStateBusiness.class);

        root = fs.getRoot();
        state.setRoot(root);
        state.setCurrentWorkingDirectory(root);

        fileA = fs.createFile();
        root.addEntry("fileA", fileA);
    }

    @Test
    void testHardLinkSuccess() throws MyFileNotFoundException, DirectoryNotFoundException, NodeAlreadyExistsException {
        boolean ok = ln.ln("fileA", "linkA");
        assertTrue(ok);

        Inode linked = root.getEntry("linkA");
        assertNotNull(linked);
        assertEquals(fileA, linked);
        assertEquals(2, linked.getLinkCount());
    }

    @Test
    void testHardLinkOnNonExistingFile() {
        assertThrows(IllegalArgumentException.class,
                () -> ln.ln("nofile", "x"));
    }

    @Test
    void testHardLinkOnDirectoryFails() {
        DirectoryInodeBusiness dir = fs.createDirectory(root);
        root.addEntry("mydir", dir);

        assertThrows(IllegalArgumentException.class,
                () -> ln.ln("mydir", "badlink"));
    }

    @Test
    void testHardLinkParentDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                () -> ln.ln("fileA", "no/such/path/linkA"));
    }

    @Test
    void testHardLinkAlreadyExists() {
        assertThrows(IllegalArgumentException.class,
                () -> ln.ln("fileA", "fileA"));
    }
}
