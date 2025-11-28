package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLnCommandBusinessTest {

    private FSLnCommandBusiness ln;
    private FSStateBusiness state;
    private FileSystem fs;

    private DirectoryInodeBusiness root;
    private FileInodeBusiness fileA;

    @BeforeEach
    void setUp() {
        state = FSStateBusiness.getInstance();
        fs = FileSystem.getInstance();

        // ricrea root
        root = new DirectoryInodeBusiness(9999);
        state.setRoot(root);
        state.setCurrentWorkingDirectory(root);

        ln = new FSLnCommandBusiness();

        // crea un file f
        fileA = fs.createFile();   // createFile() già fa incLinkCount()
        root.addEntry("fileA", fileA);
    }

    @Test
    void testHardLinkSuccess() {
        boolean ok = ln.ln("fileA", "linkA");
        assertTrue(ok);

        Inode linked = root.getEntry("linkA");
        assertNotNull(linked);
        assertEquals(fileA, linked);
        assertEquals(2, linked.getLinkCount()); // 1 (createFile) + 1 (hardlink)
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
                () -> ln.ln("fileA", "fileA")); // esiste già
    }
}