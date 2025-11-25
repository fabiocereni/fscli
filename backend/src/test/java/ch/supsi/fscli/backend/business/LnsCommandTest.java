package ch.supsi.fscli.backend.business;

//import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LnsCommandTest {

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
//        FSStateBusiness.getInstance().setRoot(root);
//    }
//
//    @Test
//    void testLnsCreatesLinkSuccessfully() throws Exception {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        assertTrue(PathSolver.resolvePath(targetPath).isPresent());
//
//        boolean result = lnCommand.lns(targetPath, linkPath);
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
//        FileBusiness softLink = (FileBusiness) linkNode.get();
//        assertTrue(softLink.isSoftLink());
//        assertEquals(targetPath, softLink.getLinkPath());
//    }
//
//    @Test
//    void testLnsFailsIfTargetDoesNotExist() {
//        String targetPath = "/dir1/nonexistent.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        DirectoryNotFoundException ex = assertThrows(DirectoryNotFoundException.class, () -> {
//            lnCommand.lns(targetPath, linkPath);
//        });
//        assertEquals("ln: softlink target does not exist", ex.getMessage());
//    }
//
//    @Test
//    void testLnsFailsIfLinkAlreadyExists() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test.txt";
//
//        NodeAlreadyExistsException ex = assertThrows(NodeAlreadyExistsException.class, () -> {
//            lnCommand.lns(targetPath, linkPath);
//        });
//        assertEquals("ln: file with same name already exists", ex.getMessage());
//    }
//
//    @Test
//    void testLnsFailsIfParentDirectoryDoesNotExist() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir2/test_link.txt";
//
//        DirectoryNotFoundException ex = assertThrows(DirectoryNotFoundException.class, () -> {
//            lnCommand.lns(targetPath, linkPath);
//        });
//        assertEquals("ln: parent directory does not exist", ex.getMessage());
//    }

}
