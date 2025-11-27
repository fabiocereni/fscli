//package ch.supsi.fscli.backend.business;
//
//import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
//import ch.supsi.fscli.backend.business.FSCommands.ln.IFSLnCommandBusiness;
//
//import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
//import ch.supsi.fscli.backend.exception.MyFileNotFoundException;
//import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LnCommandTest {
//
//    private DirectoryInodeBusiness root;
//    private DirectoryInodeBusiness dir1;
//    private FileInodeBusiness file;
//    private IFSLnCommandBusiness lnCommand;
//
//    @BeforeEach
//    void setup() {
//
//        // reset filesystem
//        FSCreationBusiness.getInstance().newfs();
//        FileSystem fs = FileSystem.getInstance();
//        FSStateBusiness state = FSStateBusiness.getInstance();
//
//        // crea root
//        root = fs.createDirectory();
//        state.setRoot(root);
//
//        // crea /dir1
//        dir1 = fs.createDirectory();
//        root.addEntry("dir1", dir1);
//
//        // crea /dir1/test.txt
//        file = fs.createFile();
//        dir1.addEntry("test.txt", file);
//
//        lnCommand = new FSLnCommandBusiness();
//    }
//
//    @Test
//    void testLnCreatesLinkSuccessfully() throws MyFileNotFoundException, DirectoryNotFoundException, NodeAlreadyExistsException {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test_link.txt";
//
//        assertTrue(PathSolver.resolvePath(targetPath).isPresent());
//
//        boolean result = lnCommand.ln(targetPath, linkPath);
//        assertTrue(result);
//
//        DirectoryInodeBusiness parentDir = PathSolver.extractParentDirectory(linkPath);
//        assertNotNull(parentDir);
//
//        Inode linkNode = parentDir.getEntry("test_link.txt");
//        assertNotNull(linkNode);
//
//        assertEquals(InodeType.FILE, linkNode.getType());
//
//        // IMPORTANTISSIMO: hard link = stesso inode!
//        assertSame(file, linkNode);
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
//
//        assertEquals("ln: target file does not exist", ex.getMessage());
//    }
//
//    @Test
//    void testLnFailsIfLinkAlreadyExists() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir1/test.txt"; // stesso nome → già esiste
//
//        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
//            lnCommand.ln(targetPath, linkPath);
//        });
//
//        assertEquals("ln: file with same name already exists", ex.getMessage());
//    }
//
//    @Test
//    void testLnFailsIfParentDirectoryDoesNotExist() {
//        String targetPath = "/dir1/test.txt";
//        String linkPath = "/dir2/test_link.txt"; // /dir2 non esiste
//
//        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
//            lnCommand.ln(targetPath, linkPath);
//        });
//
//        assertEquals("ln: parent directory does not exist", ex.getMessage());
//    }
//}
