//package ch.supsi.fscli.backend.business;
//
//import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
//import ch.supsi.fscli.backend.business.FSCommands.ln.IFSLnCommandBusiness;
//import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
//import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LnsCommandTest {
//
//    private DirectoryInodeBusiness root;
//    private DirectoryInodeBusiness dir1;
//    private FileInodeBusiness file;
//    private IFSLnCommandBusiness lnCommand;
//
//    @BeforeEach
//    void setup() {
//
//        // reset FS
//        FSCreationBusiness.getInstance().newfs();
//        FileSystem fs = FileSystem.getInstance();
//        FSStateBusiness state = FSStateBusiness.getInstance();
//
//        // root
//        root = fs.createDirectory();
//        state.setRoot(root);
//
//        // /dir1
//        dir1 = fs.createDirectory();
//        root.addEntry("dir1", dir1);
//
//        // /dir1/test.txt
//        file = fs.createFile();
//        dir1.addEntry("test.txt", file);
//
//        lnCommand = new FSLnCommandBusiness();
//    }
//
//    @Test
//    void testLnsCreatesLinkSuccessfully() throws Exception {
//
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        assertTrue(PathSolver.resolvePath(targetPath).isPresent());
//
//        boolean result = lnCommand.lns(targetPath, linkPath);
//        assertTrue(result);
//
//        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory(linkPath);
//        assertNotNull(parent);
//
//        Inode linkNode = parent.getEntry("test_link.txt");
//        assertNotNull(linkNode);
//        assertEquals(InodeType.FILE, linkNode.getType());
//
//        FileInodeBusiness softLink = (FileInodeBusiness) linkNode;
//
//        assertTrue(softLink.isSoftLink());
//        assertEquals(targetPath, softLink.getLinkPath());
//    }
//
//    @Test
//    void testLnsFailsIfTargetDoesNotExist() {
//        String targetPath = "/dir1/nonexistent.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        DirectoryNotFoundException ex =
//                assertThrows(DirectoryNotFoundException.class, () -> {
//                    lnCommand.lns(targetPath, linkPath);
//                });
//
//        assertEquals("ln: softlink target does not exist", ex.getMessage());
//    }
//
//    @Test
//    void testLnsFailsIfLinkAlreadyExists() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test.txt";
//
//        NodeAlreadyExistsException ex =
//                assertThrows(NodeAlreadyExistsException.class, () -> {
//                    lnCommand.lns(targetPath, linkPath);
//                });
//
//        assertEquals("ln: file with same name already exists", ex.getMessage());
//    }
//
//    @Test
//    void testLnsFailsIfParentDirectoryDoesNotExist() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir2/test_link.txt";
//
//        DirectoryNotFoundException ex =
//                assertThrows(DirectoryNotFoundException.class, () -> {
//                    lnCommand.lns(targetPath, linkPath);
//                });
//
//        assertEquals("ln: parent directory does not exist", ex.getMessage());
//    }
//}
