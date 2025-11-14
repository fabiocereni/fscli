package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.FSCdCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSCdCommandBusinessTest {

    private FSCdCommandBusiness cdBusiness;
    private IFSStateBusiness stateBusiness;
    private DirectoryBusiness rootDir;
    private DirectoryBusiness subDir;
    private FileBusiness testFile;

    @BeforeEach
    void setUp() {
        cdBusiness = FSCdCommandBusiness.getInstance();
        stateBusiness = FSStateBusiness.getInstance();

        rootDir = new DirectoryBusiness(null, "root");
        subDir = new DirectoryBusiness(rootDir, "documents");
        testFile = new FileBusiness(rootDir, "file.txt");

        stateBusiness.setRoot(rootDir);
        stateBusiness.setCurrentWorkingDirectory(rootDir);
    }

    @Test
    void testCdSuccess() {
        boolean result = cdBusiness.cd("documents");
        assertTrue(result);
        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());
    }

    @Test
    void testCdNotFound() {
        boolean result = cdBusiness.cd("foo");
        assertFalse(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
    }

    @Test
    void testCdToFile() {
        boolean result = cdBusiness.cd("file.txt");
        assertFalse(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
    }

    @Test
    void testCdToParent() {
        stateBusiness.setCurrentWorkingDirectory(subDir);
        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());

        boolean result = cdBusiness.cd("..");
        assertTrue(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
    }

    @Test
    void testCdToRoot() {
        stateBusiness.setCurrentWorkingDirectory(subDir);
        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());

        boolean result = cdBusiness.cd("/");
        assertTrue(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
    }
}