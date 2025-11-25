package ch.supsi.fscli.backend.business;

//import ch.supsi.fscli.backend.business.FSCommands.rmdir.FSRmdirCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSRmdirCommandBusinessTest {

//    private FSRmdirCommandBusiness rmdir;
//    private FSStateBusiness state;
//    private DirectoryBusiness root;
//
//    @BeforeEach
//    void setup() {
//        rmdir = FSRmdirCommandBusiness.getInstance();
//        state = FSStateBusiness.getInstance();
//
//        // Reset manuale (dipende da come è implementato FSStateBusiness)
//        root = new DirectoryBusiness(null, "root");
//        state.setCurrentWorkingDirectory(root);
//    }
//
//    @Test
//    void testRmdirRelativePathSuccess() {
//        DirectoryBusiness dirA = new DirectoryBusiness(root, "dirA");
//
//        boolean result = rmdir.rmdir("dirA");
//        assertTrue(result, "Directory should be removed");
//        assertFalse(root.getContent().contains(dirA), "Directory must not exist anymore");
//    }
//
//    @Test
//    void testRmdirAbsolutePathSuccess() {
//        DirectoryBusiness dirB = new DirectoryBusiness(root, "dirB");
//
//        boolean result = rmdir.rmdir("/dirB");
//        assertTrue(result, "Directory should be removed with absolute path");
//        assertFalse(root.getContent().contains(dirB));
//    }
//
//    @Test
//    void testRmdirNonEmptyDirectory() {
//        DirectoryBusiness dirC = new DirectoryBusiness(root, "dirC");
//        new DirectoryBusiness(dirC, "subdir"); // subdirectory inside dirC
//
//        boolean result = rmdir.rmdir("dirC");
//        assertFalse(result, "Should not remove non-empty directory");
//        assertTrue(root.getContent().contains(dirC));
//    }
//
//    @Test
//    void testRmdirNonExistingDirectory() {
//        boolean result = rmdir.rmdir("nonexistent");
//        assertFalse(result, "Non-existent directory should not be removed");
//    }
//
//    @Test
//    void testRmdirRoot() {
//        boolean result = rmdir.rmdir("/root");
//        assertFalse(result, "Should not remove root directory");
//    }
}