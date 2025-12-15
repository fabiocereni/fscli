package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PathSolverTest {
    private FileSystem fs;
    private PathSolver pathSolver;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness home;
    private DirectoryInodeBusiness user;
    private FileInodeBusiness file;

    @BeforeEach
    void setUp() {
        fs = new FileSystem();
        pathSolver = new PathSolver(fs);

        root = fs.getRoot();

        home = new DirectoryInodeBusiness(200);
        user = new DirectoryInodeBusiness(300);
        DirectoryInodeBusiness docs = new DirectoryInodeBusiness(400);
        DirectoryInodeBusiness bin  = new DirectoryInodeBusiness(500);

        file = fs.createFile();

        root.addEntry("home", home);
        root.addEntry("bin", bin);

        home.addEntry("user", user);

        user.addEntry("docs", docs);
        user.addEntry("file.txt", file);

        fs.setCurrentWorkingDirectory(root);
    }

    @Test
    void testResolveRoot() {
        Optional<Inode> result = pathSolver.resolvePath("/");
        assertTrue(result.isPresent());
        assertEquals(root, result.get());
    }

    @Test
    void testResolveAbsolutePathSuccess() {
        Optional<Inode> result = pathSolver.resolvePath("/home/user/file.txt");
        assertTrue(result.isPresent());
        assertEquals(file, result.get());
    }

    @Test
    void testResolveRelativePathFromRoot() {
        Optional<Inode> result = pathSolver.resolvePath("home/user");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testResolveRelativePathFromSubDir() {
        fs.setCurrentWorkingDirectory(home);

        Optional<Inode> result = pathSolver.resolvePath("user/file.txt");
        assertTrue(result.isPresent());
        assertEquals(file, result.get());
    }

    @Test
    void testResolvePathWithDots() {
        Optional<Inode> result = pathSolver.resolvePath("/home/./user");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testResolvePathWithDoubleDotsInString() {
        Optional<Inode> result = pathSolver.resolvePath("/home/user/../user/file.txt");
        assertTrue(result.isPresent());
        assertEquals(file, result.get());
    }

    @Test
    void testResolvePathNotFound() {
        Optional<Inode> result = pathSolver.resolvePath("/home/user/nonEsiste.txt");
        assertTrue(result.isEmpty());
    }

    @Test
    void testResolvePathInvalidDoubleSlash() {
        Optional<Inode> result = pathSolver.resolvePath("/home//user");
        assertTrue(result.isEmpty());
    }

    @Test
    void testResolveNullOrEmpty() {
        assertTrue(pathSolver.resolvePath(null).isEmpty());
        assertTrue(pathSolver.resolvePath("").isEmpty());
    }


    @Test
    void testExtractParentFromFile() {
        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("/home/user/file.txt");
        assertNotNull(parent);
        assertEquals(user, parent);
    }

    @Test
    void testExtractParentFromDirectoryWithTrailingSlash() {
        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("/home/user/");
        assertNotNull(parent);
        assertEquals(home, parent);
    }

    @Test
    void testExtractParentRoot() {
        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("/fileInRoot");
        assertEquals(root, parent);
    }

    @Test
    void testExtractParentSimpleName() {
        fs.setCurrentWorkingDirectory(user);

        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("file.txt");
        assertEquals(user, parent);
    }

    @Test
    void testExtractParentNotExists() {
        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("/home/path/file.txt");
        assertNull(parent);
    }


    @Test
    void testExtractFileNameAbsolute() {
        assertEquals("file.txt", pathSolver.extractFileName("/home/user/file.txt"));
    }

    @Test
    void testExtractFileNameSimple() {
        assertEquals("readme.txt", pathSolver.extractFileName("readme.txt"));
    }


    @Test
    void testNameAlreadyExists() {
        assertTrue(pathSolver.nameAlreadyExists(user, "file.txt"));
        assertFalse(pathSolver.nameAlreadyExists(user, "file2.txt"));
    }
}
