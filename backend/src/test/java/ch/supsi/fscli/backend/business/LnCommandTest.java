package ch.supsi.fscli.backend.business;

//import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LnCommandTest {

//    private DirectoryBusiness root;
//    private DirectoryBusiness dir1;
//    private FileBusiness file;
//    private FSLnCommandBusiness lnCommand;
//
//    @BeforeEach
//    void setup() {
//        root = new DirectoryBusiness(null, "root");
//        dir1 = new DirectoryBusiness(root, "dir1");
//        file = new FileBusiness(dir1, "test.txt");
//
//        lnCommand = new FSLnCommandBusiness();
//
//        FSStateBusiness.getInstance().setRoot(root);
//    }
//
//    @Test
//    void testLnCreatesLinkSuccessfully() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        assertTrue(PathSolver.resolvePath(targetPath).isPresent());
//
//        boolean result = lnCommand.ln(targetPath, linkPath);
//        assertTrue(result);
//
//        IDirectoryBusiness parentDir = PathSolver.extractParentDirectory(linkPath);
//        assertNotNull(parentDir);
//
//        Optional<INode> linkNode = parentDir.getContent().stream()
//                .filter(n -> n.getName().equals("test_link.txt"))
//                .findFirst();
//
//        assertTrue(linkNode.isPresent());
//        assertEquals(InodeType.FILE, linkNode.get().getType());
//
//    }
//
//    @Test
//    void testLnFailsIfTargetDoesNotExist() {
//        String targetPath = "/dir1/nonexistent.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
//            lnCommand.ln(targetPath, linkPath);
//        });
//        assertEquals("ln: target file does not exist", ex.getMessage());
//    }
//
//    @Test
//    void testLnFailsIfLinkAlreadyExists() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test.txt";
//
//        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
//            lnCommand.ln(targetPath, linkPath);
//        });
//        assertEquals("ln: file with same name already exists", ex.getMessage());
//    }
//
//    @Test
//    void testLnFailsIfParentDirectoryDoesNotExist() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir2/test_link.txt";
//
//        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
//            lnCommand.ln(targetPath, linkPath);
//        });
//        assertEquals("ln: parent directory does not exist", ex.getMessage());
//    }
}
