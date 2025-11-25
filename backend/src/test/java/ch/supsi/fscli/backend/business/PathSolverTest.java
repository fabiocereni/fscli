package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PathSolverTest {

    private FileSystem fileSystem = FileSystem.getInstance();
    private IFSStateBusiness ifsState = FSStateBusiness.getInstance();

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness dirA;
    private DirectoryInodeBusiness dirB;
    private FileInodeBusiness fileX;

    @BeforeEach
    public void setup() {
        FSCreationBusiness.getInstance().newfs();
        fileSystem = FileSystem.getInstance();

        // root
        root = ifsState.getRoot();
        assertNotNull(root);

        // crea:  /A , /A/B , /A/B/x.txt
        dirA = fileSystem.createDirectory();
        root.addEntry("A", dirA);

        dirB = fileSystem.createDirectory();
        dirA.addEntry("B", dirB);

        fileX = fileSystem.createFile();
        dirB.addEntry("x.txt", fileX);

        // set CWD = root
        ifsState.setCurrentWorkingDirectory(root);
    }


    @Test
    public void testResolveRoot() {
        Optional<Inode> res = PathSolver.resolvePath("/");
        assertTrue(res.isPresent());
        assertEquals(root, res.get());
    }

    @Test
    public void testResolveAbsolutePath() {
        Optional<Inode> res = PathSolver.resolvePath("/A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testResolveRelativePath() {
        // CWD = root, quindi "A/B/x.txt" → /A/B/x.txt
        Optional<Inode> res = PathSolver.resolvePath("A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testResolveWithDot() {
        Optional<Inode> res = PathSolver.resolvePath("./A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testResolveWithDotDot() {
        // CWD = root → ".." = root
        Optional<Inode> res = PathSolver.resolvePath("../A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testResolveInvalidPath() {
        Optional<Inode> res = PathSolver.resolvePath("/A/B/DOES_NOT_EXIST");
        assertTrue(res.isEmpty());
    }

    @Test
    public void testRejectDoubleSlash() {
        Optional<Inode> res = PathSolver.resolvePath("/A//B");
        assertTrue(res.isEmpty());
    }



    @Test
    public void testParentOfAbsoluteFile() {
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/A/B/x.txt");
        assertNotNull(parent);
        assertEquals(dirB, parent);
    }

    @Test
    public void testParentOfRelativeFile() {
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("A/B/x.txt");
        assertNotNull(parent);
        assertEquals(dirB, parent);
    }

    @Test
    public void testParentOfFileInRoot() {
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/file");
        assertEquals(root, parent);
    }

    @Test
    public void testParentOfLocalFileName() {
        // senza slash → parent = CWD = root
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("localFile");
        assertEquals(root, parent);
    }

    @Test
    public void testParentWithInvalidPath() {
        assertNull(PathSolver.extractParentDirectory("/A//B/x"));
    }


    @Test
    public void testExtractFileName() {
        assertEquals("x.txt", PathSolver.extractFileName("/A/B/x.txt"));
        assertEquals("file", PathSolver.extractFileName("file"));
        assertEquals("dir", PathSolver.extractFileName("/dir"));
    }



    @Test
    public void testNameAlreadyExists() {
        assertTrue(PathSolver.nameAlreadyExists(dirB, "x.txt"));
        assertFalse(PathSolver.nameAlreadyExists(dirB, "missing"));
    }

    @Test
    public void testResolveDotDotInsidePath() {
        Optional<Inode> res = PathSolver.resolvePath("/A/B/../B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testResolveMultipleDotDot() {
        Optional<Inode> res = PathSolver.resolvePath("/A/B/../../A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testResolveDotDotAboveRoot() {
        Optional<Inode> res = PathSolver.resolvePath("/../../A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }

    @Test
    public void testRelativeDotDotWhenCwdIsRoot() {
        ifsState.setCurrentWorkingDirectory(root);
        Optional<Inode> res = PathSolver.resolvePath("../A/B/x.txt");
        assertTrue(res.isPresent());
        assertEquals(fileX, res.get());
    }
    @Test
    public void testRelativePathWithManyDotDot() {
        Optional<Inode> res = PathSolver.resolvePath("A/B/../../..");
        // CWD = root → risultato = root
        assertTrue(res.isPresent());
        assertEquals(root, res.get());
    }


    @Test
    public void testResolveDirectoryEndingSlash() {
        Optional<Inode> res = PathSolver.resolvePath("/A/B/");
        assertTrue(res.isPresent());
        assertEquals(dirB, res.get());
    }


    @Test
    public void testResolveNull() {
        assertTrue(PathSolver.resolvePath(null).isEmpty());
    }

    @Test
    public void testResolveBlank() {
        assertTrue(PathSolver.resolvePath("   ").isEmpty());
    }

    @Test
    public void testResolveOnlyDots() {
        Optional<Inode> res = PathSolver.resolvePath("..");
        // CWD = root → .. = root
        assertTrue(res.isPresent());
        assertEquals(root, res.get());
    }

    @Test
    public void testResolveOnlyDot() {
        Optional<Inode> res = PathSolver.resolvePath(".");
        // "." → CWD = root
        assertTrue(res.isPresent());
        assertEquals(root, res.get());
    }



    @Test
    public void testParentOfPathEndingSlash() {
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/A/B/");

        assertEquals(dirA, parent);
    }

    @Test
    public void testParentOfDeepPathWithDotDot() {
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/A/B/../B/x.txt");
        assertEquals(dirB, parent);
    }

    @Test
    public void testParentOfInvalidParent() {
        // "/A/NOPE/file"
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/A/NOPE/file");
        assertNull(parent);
    }


    @Test
    public void testExtractFileNameEndingSlash() {
        assertEquals("", PathSolver.extractFileName("/A/B/"));
    }

    @Test
    public void testExtractFileNameMultipleSlashes() {
        assertEquals("x.txt", PathSolver.extractFileName("////A////B////x.txt"));
    }

}
