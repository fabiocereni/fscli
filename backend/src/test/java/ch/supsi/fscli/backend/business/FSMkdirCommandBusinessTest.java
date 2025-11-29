package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.mkdir.IFSMkdirCommandBusiness;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSMkdirCommandBusinessTest {

    private Injector injector;
    private IFSMkdirCommandBusiness mkdirBusiness;
    private FileSystem fileSystem;
    private IFSStateBusiness ifsState;
    private IFSCreationBusiness creation;

    private DirectoryInodeBusiness root;

    @BeforeEach
    void setup() {

        injector = Guice.createInjector(new FileSystemModule());

        mkdirBusiness = injector.getInstance(IFSMkdirCommandBusiness.class);
        fileSystem = injector.getInstance(FileSystem.class);
        ifsState = injector.getInstance(IFSStateBusiness.class);
        creation = injector.getInstance(IFSCreationBusiness.class);

        // Ricrea il filesystem da zero (come faceva newfs())
        creation.newfs();

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
        assertFalse(mkdirBusiness.mkdir("dup"));
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
        root.addEntry("file", fileSystem.createFile());
        assertFalse(mkdirBusiness.mkdir("/file/sub"));
    }

    // ----------------------------------------------------------
    // 6) Invalid name extraction
    // ----------------------------------------------------------
    @Test
    void testCreateEmptyNameAtEnd() {
        assertFalse(mkdirBusiness.mkdir("/abc/"));
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
