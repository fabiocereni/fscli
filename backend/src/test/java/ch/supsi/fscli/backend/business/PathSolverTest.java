package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PathSolverTest {

    private IFSStateBusiness fsState;

    @BeforeEach
    void setup() {
        FSStateBusiness.getInstance().setRoot(new DirectoryBusiness(null, "root"));
        DirectoryBusiness root = FSStateBusiness.getInstance().getRoot();
        root.getContent().clear();
        DirectoryBusiness home = new DirectoryBusiness(root, "home");
        DirectoryBusiness user = new DirectoryBusiness(home, "user");
        FileBusiness file1 = new FileBusiness(user, "file.txt");
        FSStateBusiness.getInstance().setCurrentWorkingDirectory(user);

        fsState = FSStateBusiness.getInstance();


        root.addContent(home);
        home.addContent(user);
        user.addContent(file1);

        fsState.setCurrentWorkingDirectory(user);
    }

    @Test
    void testAbsoluteDirectory() {
        Optional<INode> result = PathSolver.resolvePath("/home/user");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getName());
    }

//    @Test
//    void testAbsoluteFileReturnsParent() {
//        Optional<INode> result = PathSolver.resolvePath("/home/user/file.txt");
//        assertTrue(result.isPresent());
//        assertEquals("user", result.get().getName());
//    }
//
//    @Test
//    void testRelativeFileReturnsParent() {
//        Optional<INode> result = PathSolver.resolvePath("file.txt");
//        assertTrue(result.isPresent());
//        assertEquals("user", result.get().getName());
//    }

    @Test
    void testRelativeDirectoryDot() {
        Optional<INode> result = PathSolver.resolvePath(".");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getName());
    }

    @Test
    void testParentDirectoryToken() {
        Optional<INode> result = PathSolver.resolvePath("..");
        assertTrue(result.isPresent());
        assertEquals("home", result.get().getName());
    }

//    @Test
//    void testParentDirectoryFileToken() {
//        Optional<INode> result = PathSolver.resolvePath("../user/file.txt");
//        assertTrue(result.isPresent());
//        assertEquals("user", result.get().getName());
//    }
//
//    @Test
//    void testMultipleSlashes() {
//        Optional<INode> result = PathSolver.resolvePath("/home//user///file.txt");
//        assertTrue(result.isPresent());
//        assertEquals("user", result.get().getName());
//    }

    @Test
    void testNonExistentPath() {
        Optional<INode> result = PathSolver.resolvePath("/home/other/file.txt");
        assertTrue(result.isEmpty());
    }

    @Test
    void testRootPath() {
        Optional<INode> result = PathSolver.resolvePath("/");
        assertTrue(result.isPresent());
        assertEquals("root", result.get().getName());
    }

    @Test
    void testNullPath() {
        Optional<INode> result = PathSolver.resolvePath(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptyPath() {
        Optional<INode> result = PathSolver.resolvePath("");
        assertTrue(result.isEmpty());
    }
}
