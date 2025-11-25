package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.mkdir.FSMkdirCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSMkdirCommandBusinessTest {

    private FSMkdirCommandBusiness mkdirBusiness;
    private IFSStateBusiness stateBusiness;
    private DirectoryBusiness root;

    @BeforeEach
    void setUp() {
        mkdirBusiness = FSMkdirCommandBusiness.getInstance();
        stateBusiness = FSStateBusiness.getInstance();

        // reset stato file system per il test
        root = new DirectoryBusiness(null, "root");
        stateBusiness.setRoot(root);
        stateBusiness.setCurrentWorkingDirectory(root);
    }

    @Test
    void testSimpleMkdir() {
        boolean result = mkdirBusiness.mkdir("testDir");

        assertTrue(result);
        assertTrue(root.getContent().stream().anyMatch(n -> n.getName().equals("testDir")));
    }

    @Test
    void testMkdirAlreadyExists() {
        mkdirBusiness.mkdir("dup");
        boolean result = mkdirBusiness.mkdir("dup");

        assertFalse(result);
    }

    @Test
    void testMkdirRelativePath() {
        mkdirBusiness.mkdir("sub");
        stateBusiness.setCurrentWorkingDirectory(
                (DirectoryBusiness) root.getContent().stream()
                        .filter(n -> n.getName().equals("sub"))
                        .findFirst().get()
        );

        boolean result = mkdirBusiness.mkdir("inner");

        assertTrue(result);

        DirectoryBusiness sub = (DirectoryBusiness) root.getContent().stream()
                .filter(n -> n.getName().equals("sub")).findFirst().get();

        assertTrue(sub.getContent().stream().anyMatch(n -> n.getName().equals("inner")));
    }

    @Test
    void testMkdirAbsolutePath() {
        boolean result = mkdirBusiness.mkdir("/absDir");

        assertTrue(result);

        assertTrue(root.getContent().stream().anyMatch(n -> n.getName().equals("absDir")));
    }

    @Test
    void testMkdirParentDoesNotExist() {
        boolean result = mkdirBusiness.mkdir("/no/such/path/newDir");

        assertFalse(result);
    }

    @Test
    void testMkdirWherePartIsFile() {
        // Create a file in root
        new FileBusiness(root, "myFile");

        boolean result = mkdirBusiness.mkdir("myFile/newDir");

        assertFalse(result);
    }

    @Test
    void testMkdirInvalidDoubleSlash() {
        boolean result = mkdirBusiness.mkdir("/bad//path");

        assertFalse(result);
    }
}