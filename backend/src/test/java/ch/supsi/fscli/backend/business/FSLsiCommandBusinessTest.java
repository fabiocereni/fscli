package ch.supsi.fscli.backend.business;

//import ch.supsi.fscli.backend.business.FSCommands.ls.FSLsCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLsiCommandBusinessTest {

//    private FSLsCommandBusiness lsCommand;
//    private FSStateBusiness stateBusiness;
//    private DirectoryBusiness rootDir;
//    private DirectoryBusiness subDir;
//    private FileBusiness file;
//
//    @BeforeEach
//    void setUp() {
//        lsCommand = FSLsCommandBusiness.getInstance();
//        stateBusiness = FSStateBusiness.getInstance();
//
//        // reset stato file system
//        rootDir = new DirectoryBusiness(null, "root");
//        stateBusiness.setRoot(rootDir);
//        stateBusiness.setCurrentWorkingDirectory(rootDir);
//
//        subDir = new DirectoryBusiness(rootDir, "subdir");
//        file = new FileBusiness(rootDir, "file.txt");
//    }
//
//    @Test
//    void testLsOnRootDirectoryWithoutId() {
//        String result = lsCommand.ls("/", false);
//        assertTrue(result.contains("subdir"));
//        assertTrue(result.contains("file.txt"));
//        assertFalse(result.contains("["));
//    }
//
//    @Test
//    void testLsOnRootDirectoryWithId() {
//        String result = lsCommand.ls("/", true);
//        assertTrue(result.contains("subdir ["));
//        assertTrue(result.contains("file.txt ["));
//    }
//
//    @Test
//    void testLsOnFile() {
//        String result = lsCommand.ls("file.txt", false);
//        assertEquals("That is not a directory", result);
//    }
//
//    @Test
//    void testLsNonExisting() {
//        String result = lsCommand.ls("nonexistent", false);
//        assertEquals("Directory not found", result);
//    }
//
//    @Test
//    void testLsCurrentDirectoryWithNullName() {
//        String result = lsCommand.ls(null, false);
//        assertTrue(result.contains("subdir"));
//        assertTrue(result.contains("file.txt"));
//    }
}