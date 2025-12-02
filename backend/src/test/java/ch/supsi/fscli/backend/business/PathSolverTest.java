package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PathSolverTest {

    private Injector injector;

    private IFSStateBusiness state;
    private FileSystem fs;
    private PathSolver pathSolver;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness home;
    private DirectoryInodeBusiness user;
    private FileInodeBusiness photo;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        pathSolver = injector.getInstance(PathSolver.class);

        state = injector.getInstance(IFSStateBusiness.class);
        fs    = injector.getInstance(FileSystem.class);

        // reset filesystem
        injector.getInstance(IFSCreationBusiness.class).newfs();

        root = state.getRoot();

        home = new DirectoryInodeBusiness(200);
        user = new DirectoryInodeBusiness(300);
        DirectoryInodeBusiness docs = new DirectoryInodeBusiness(400);
        DirectoryInodeBusiness bin  = new DirectoryInodeBusiness(500);

        photo = fs.createFile();

        root.addEntry("home", home);
        root.addEntry("bin", bin);

        home.addEntry("user", user);

        user.addEntry("docs", docs);
        user.addEntry("photo.jpg", photo);

        state.setCurrentWorkingDirectory(root);
    }

    // --- TEST RESOLVE PATH ---

    @Test
    void testResolveRoot() {
        Optional<Inode> result = pathSolver.resolvePath("/");
        assertTrue(result.isPresent());
        assertEquals(root, result.get());
    }

    @Test
    void testResolveAbsolutePathSuccess() {
        Optional<Inode> result = pathSolver.resolvePath("/home/user/photo.jpg");
        assertTrue(result.isPresent());
        assertEquals(photo, result.get());
    }

    @Test
    void testResolveRelativePathFromRoot() {
        Optional<Inode> result = pathSolver.resolvePath("home/user");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testResolveRelativePathFromSubDir() {
        state.setCurrentWorkingDirectory(home);

        Optional<Inode> result = pathSolver.resolvePath("user/photo.jpg");
        assertTrue(result.isPresent());
        assertEquals(photo, result.get());
    }

    @Test
    void testResolvePathWithDots() {
        Optional<Inode> result = pathSolver.resolvePath("/home/./user");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testResolvePathWithDoubleDotsInString() {
        Optional<Inode> result = pathSolver.resolvePath("/home/user/../user/photo.jpg");
        assertTrue(result.isPresent());
        assertEquals(photo, result.get());
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

    // --- TEST EXTRACT PARENT DIRECTORY ---

    @Test
    void testExtractParentFromFile() {
        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("/home/user/photo.jpg");
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
        state.setCurrentWorkingDirectory(user);

        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("photo.jpg");
        assertEquals(user, parent);
    }

    @Test
    void testExtractParentNotExists() {
        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory("/home/ghost/file.txt");
        assertNull(parent);
    }

    // --- TEST EXTRACT FILE NAME ---

    @Test
    void testExtractFileNameAbsolute() {
        assertEquals("photo.jpg", pathSolver.extractFileName("/home/user/photo.jpg"));
    }

    @Test
    void testExtractFileNameSimple() {
        assertEquals("readme.txt", pathSolver.extractFileName("readme.txt"));
    }

    // --- TEST NAME ALREADY EXISTS ---

    @Test
    void testNameAlreadyExists() {
        assertTrue(pathSolver.nameAlreadyExists(user, "photo.jpg"));
        assertFalse(pathSolver.nameAlreadyExists(user, "video.mp4"));
    }
}
