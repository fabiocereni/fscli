package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.cd.FSCdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSCdCommandBusinessTest {

    private FSCdCommandBusiness cdBusiness;
    private PathSolver pathSolver;
    private FileSystem fs;

    private DirectoryInodeBusiness rootDir;
    private DirectoryInodeBusiness subDir;
    private FileInodeBusiness testFile;

    @BeforeEach
    void setUp() {
        rootDir = new DirectoryInodeBusiness(1L);

        fs = new FileSystem();
        fs.setRoot(rootDir);
        fs.setCurrentWorkingDirectory(rootDir);
        fs.setCurrentWorkingDirectoryPath("/");

        pathSolver = new PathSolver(fs);

        cdBusiness = new FSCdCommandBusiness(fs, pathSolver);

        subDir = fs.createDirectory();
        rootDir.addEntry("documents", subDir);

        testFile = fs.createFile();
        rootDir.addEntry("file.txt", testFile);
    }

    @Test
    void testCdSuccess() {
        boolean result = cdBusiness.cd("documents");

        assertTrue(result);
        assertEquals(subDir, fs.getCurrentWorkingDirectory());
        assertEquals("/documents", fs.getCurrentWorkingDirectoryPath());
    }

    @Test
    void testCdNotFound() {
        boolean result = cdBusiness.cd("foo");

        assertFalse(result);
        assertEquals(rootDir, fs.getCurrentWorkingDirectory());
        assertEquals("/", fs.getCurrentWorkingDirectoryPath());
    }

    @Test
    void testCdToFile() {
        boolean result = cdBusiness.cd("file.txt");

        assertFalse(result);
        assertEquals(rootDir, fs.getCurrentWorkingDirectory());
    }

    @Test
    void testCdToParent() {
        fs.setCurrentWorkingDirectory(subDir);
        fs.setCurrentWorkingDirectoryPath("/documents");

        boolean result = cdBusiness.cd("..");

        assertTrue(result);
        assertEquals(rootDir, fs.getCurrentWorkingDirectory(), "Dovrebbe tornare alla root");
        assertEquals("/", fs.getCurrentWorkingDirectoryPath(), "Il path dovrebbe essere /");
    }

    @Test
    void testCdToRoot() {
        fs.setCurrentWorkingDirectory(subDir);
        fs.setCurrentWorkingDirectoryPath("/documents");

        boolean result = cdBusiness.cd("/");

        assertTrue(result);
        assertEquals(rootDir, fs.getCurrentWorkingDirectory());
        assertEquals("/", fs.getCurrentWorkingDirectoryPath());
    }

    @Test
    void testCdComplexPath() {
        DirectoryInodeBusiness workDir = fs.createDirectory();
        subDir.addEntry("work", workDir);

        cdBusiness.cd("documents/work");
        assertEquals(workDir, fs.getCurrentWorkingDirectory());
        assertEquals("/documents/work", fs.getCurrentWorkingDirectoryPath());

        cdBusiness.cd("../..");
        assertEquals(rootDir, fs.getCurrentWorkingDirectory());
        assertEquals("/", fs.getCurrentWorkingDirectoryPath());
    }
}