//package ch.supsi.fscli.backend.business;
//
//
//import ch.supsi.fscli.backend.business.FSCommands.cd.FSCdCommandBusiness;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FSCdCommandBusinessTest {
//
//    private FSCdCommandBusiness cdBusiness;
//    private IFSStateBusiness stateBusiness;
//    private DirectoryInodeBusiness rootDir;
//    private DirectoryInodeBusiness subDir;
//    private FileInodeBusiness testFile;
//
//
//    @BeforeEach
//    void setUp() {
//
//        FSCreationBusiness.getInstance().newfs();
//
//        stateBusiness = FSStateBusiness.getInstance();
//
//        FileSystem fs = FileSystem.getInstance();
//
//        cdBusiness = FSCdCommandBusiness.getInstance();
//
//        // Crea la root directory
//        rootDir = fs.createDirectory();
//        stateBusiness.setRoot(rootDir);
//        stateBusiness.setCurrentWorkingDirectory(rootDir);
//
//        // Crea /documents
//        subDir = fs.createDirectory();
//        rootDir.addEntry("documents", subDir);
//
//        // Crea /file.txt
//        testFile = fs.createFile();
//        rootDir.addEntry("file.txt", testFile);
//    }
//
//
//    @Test
//    void testCdSuccess() {
//        boolean result = cdBusiness.cd("documents");
//        assertTrue(result);
//        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());
//    }
//
//    @Test
//    void testCdNotFound() {
//        boolean result = cdBusiness.cd("foo");
//        assertFalse(result);
//        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
//    }
//
//    @Test
//    void testCdToFile() {
//        boolean result = cdBusiness.cd("file.txt");
//        assertFalse(result);
//        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
//    }
//
//    // TODO da controllare
//    @Test
//    void testCdToParent() {
//        stateBusiness.setCurrentWorkingDirectory(subDir);
//        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());
//
//        boolean result = cdBusiness.cd("..");
//        assertTrue(result);
//        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
//    }
//
//    @Test
//    void testCdToRoot() {
//        stateBusiness.setCurrentWorkingDirectory(subDir);
//        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());
//
//        boolean result = cdBusiness.cd("/");
//        assertTrue(result);
//        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
//    }
//}