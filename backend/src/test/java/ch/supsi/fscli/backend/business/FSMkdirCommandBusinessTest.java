package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.mkdir.FSMkdirCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSMkdirCommandBusinessTest {

    private FileSystem fileSystem;
    private IFSStateBusiness ifsState;
    private DirectoryInodeBusiness root;
    private FSMkdirCommandBusiness mkdirBusiness;

    @BeforeEach
    void setup() {
        // Reset full filesystem
        FSCreationBusiness.getInstance().newfs();

        fileSystem = FileSystem.getInstance();
        ifsState = FSStateBusiness.getInstance();
        mkdirBusiness = FSMkdirCommandBusiness.getInstance();

        root = ifsState.getRoot();
        assertNotNull(root);

        ifsState.setCurrentWorkingDirectory(root);
    }

    // ----------------------------------------------------------
    // 1) Invalid input
    // ----------------------------------------------------------
    @Test
    void testNullPath() {
        assertFalse(mkdirBusiness.mkdir(null));
    }

    @Test
    void testBlankPath() {
        assertFalse(mkdirBusiness.mkdir(""));
        assertFalse(mkdirBusiness.mkdir("   "));
    }

    // ----------------------------------------------------------
    // 2) Basic creation in root
    // ----------------------------------------------------------
    @Test
    void testCreateDirectoryInRoot() {
        assertTrue(mkdirBusiness.mkdir("folder"));
        assertNotNull(root.getEntry("folder"));
        assertTrue(root.getEntry("folder") instanceof DirectoryInodeBusiness);
    }

    @Test
    void testCreateDirectoryAlreadyExists() {
        assertTrue(mkdirBusiness.mkdir("dup"));
        assertFalse(mkdirBusiness.mkdir("dup"));  // duplicate name
    }

    // ----------------------------------------------------------
    // 3) Relative path creation
    // ----------------------------------------------------------
    @Test
    void testRelativePathCreation() {
        mkdirBusiness.mkdir("a");
        DirectoryInodeBusiness a = (DirectoryInodeBusiness) root.getEntry("a");
        ifsState.setCurrentWorkingDirectory(a);

        assertTrue(mkdirBusiness.mkdir("b"));
        assertNotNull(a.getEntry("b"));
    }

    @Test
    void testRelativePathWithSubpath() {
        mkdirBusiness.mkdir("a");
        mkdirBusiness.mkdir("a/b");
        DirectoryInodeBusiness a = (DirectoryInodeBusiness) root.getEntry("a");
        assertNotNull(a.getEntry("b"));
    }

    // ----------------------------------------------------------
    // 4) Absolute path creation
    // ----------------------------------------------------------
    @Test
    void testAbsolutePathCreation() {
        assertTrue(mkdirBusiness.mkdir("/x"));
        assertNotNull(root.getEntry("x"));
    }

    @Test
    void testAbsoluteNestedPathCreation() {
        mkdirBusiness.mkdir("/p");
        assertTrue(mkdirBusiness.mkdir("/p/q"));
        DirectoryInodeBusiness p = (DirectoryInodeBusiness) root.getEntry("p");
        assertNotNull(p.getEntry("q"));
    }

    // ----------------------------------------------------------
    // 5) Invalid parent path
    // ----------------------------------------------------------
    @Test
    void testInvalidParentPath() {
        assertFalse(mkdirBusiness.mkdir("/does/not/exist/newdir"));
    }

    @Test
    void testParentIsFile() {
        root.addEntry("file", fileSystem.createFile()); // add file "file"
        assertFalse(mkdirBusiness.mkdir("/file/sub"));
    }

    // ----------------------------------------------------------
    // 6) Invalid name extraction
    // ----------------------------------------------------------
    @Test
    void testCreateEmptyNameAtEnd() {
        assertFalse(mkdirBusiness.mkdir("/abc/"));  // last token empty
    }

    @Test
    void testOnlySlash() {
        assertFalse(mkdirBusiness.mkdir("/"));
    }

    // ----------------------------------------------------------
    // 7) Ensure state is not corrupted
    // ----------------------------------------------------------
    @Test
    void testNoSideEffectsWhenFailing() {
        int before = root.getEntries().size();
        assertFalse(mkdirBusiness.mkdir("/invalid//path"));
        int after = root.getEntries().size();
        assertEquals(before, after);
    }
}
