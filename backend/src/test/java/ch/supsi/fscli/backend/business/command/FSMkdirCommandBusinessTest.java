package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.mkdir.FSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.mkdir.IFSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSMkdirCommandBusinessTest {

    @Inject
    private IFSMkdirCommandBusiness mkdirBusiness;
    private FileSystem fileSystem;


    private DirectoryInodeBusiness root;

    @BeforeEach
    void setup() {

        fileSystem = new FileSystem();

        mkdirBusiness = new FSMkdirCommandBusiness(fileSystem, new PathSolver(fileSystem));

        root = fileSystem.getRoot();
        assertNotNull(root);

        fileSystem.setCurrentWorkingDirectory(root);
    }

    @Test
    void testNullPath() {
        assertFalse(mkdirBusiness.mkdir(null));
    }

    @Test
    void testBlankPath() {
        assertFalse(mkdirBusiness.mkdir(""));
        assertFalse(mkdirBusiness.mkdir("   "));
    }

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

    @Test
    void testRelativePathCreation() {
        mkdirBusiness.mkdir("a");
        DirectoryInodeBusiness a = (DirectoryInodeBusiness) root.getEntry("a");
        fileSystem.setCurrentWorkingDirectory(a);

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

    @Test
    void testInvalidParentPath() {
        assertFalse(mkdirBusiness.mkdir("/does/not/exist/newdir"));
    }

    @Test
    void testParentIsFile() {
        root.addEntry("file", fileSystem.createFile());
        assertFalse(mkdirBusiness.mkdir("/file/sub"));
    }

    @Test
    void testCreateEmptyNameAtEnd() {
        assertFalse(mkdirBusiness.mkdir("/abc/"));
    }

    @Test
    void testOnlySlash() {
        assertFalse(mkdirBusiness.mkdir("/"));
    }

    @Test
    void testNoSideEffectsWhenFailing() {
        int before = root.getEntries().size();
        assertFalse(mkdirBusiness.mkdir("/invalid//path"));
        int after = root.getEntries().size();
        assertEquals(before, after);
    }
}
