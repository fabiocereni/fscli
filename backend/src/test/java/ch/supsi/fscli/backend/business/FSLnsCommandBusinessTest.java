package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLnsCommandBusinessTest {

    private FSLnCommandBusiness ln;
    private FSStateBusiness state;
    private FileSystem fs;

    private DirectoryInodeBusiness root;
    private FileInodeBusiness fileA;

    @BeforeEach
    void setUp() {
        state = FSStateBusiness.getInstance();
        fs = FileSystem.getInstance();

        // reset root manuale
        root = new DirectoryInodeBusiness(5000);
        state.setRoot(root);
        state.setCurrentWorkingDirectory(root);

        ln = new FSLnCommandBusiness();

        // crea file
        fileA = fs.createFile();  // createFile() già fa incLinkCount()
        root.addEntry("fileA", fileA);
    }

    @Test
    void testSoftLinkSuccess() throws Exception {
        boolean ok = ln.lns("fileA", "softA");
        assertTrue(ok);

        Inode inode = root.getEntry("softA");
        assertNotNull(inode);
        assertInstanceOf(FileInodeBusiness.class, inode);

        FileInodeBusiness soft = (FileInodeBusiness) inode;
        assertTrue(soft.isSoftLink());
        assertEquals("fileA", soft.getLinkPath());
        assertEquals(1, soft.getLinkCount()); // 1 (createFile)
    }

    @Test
    void testSoftLinkTargetDoesNotExist() {
        assertThrows(DirectoryNotFoundException.class,
                () -> ln.lns("nope", "softX"));
    }

    @Test
    void testSoftLinkParentDoesNotExist() {
        assertThrows(DirectoryNotFoundException.class,
                () -> ln.lns("fileA", "missing/soft"));
    }

    @Test
    void testSoftLinkAlreadyExists() {
        assertThrows(NodeAlreadyExistsException.class,
                () -> ln.lns("fileA", "fileA")); // esiste già
    }

    @Test
    void testSoftLinkDoesNotFollowDirectoryRules() throws Exception {
        // soft link to fileA in root
        ln.lns("fileA", "alias");

        Inode link = root.getEntry("alias");
        assertNotNull(link);
        assertEquals(InodeType.FILE, link.getType()); // NON deve diventare DIRECTORY
    }
}