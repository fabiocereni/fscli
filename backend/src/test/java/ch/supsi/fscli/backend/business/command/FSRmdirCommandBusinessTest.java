package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.FSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSRmdirCommandBusinessTest {

    private IFSRmdirCommandBusiness rmdir;

    private FileSystem fs;

    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {
        fs = new FileSystem();
        rmdir = new FSRmdirCommandBusiness(fs, new PathSolver(fs));

        root = fs.getRoot();
        assertNotNull(root);

        fs.setCurrentWorkingDirectory(root);
        fs.setCurrentWorkingDirectoryPath("/");
    }

    @Test
    void testRemoveEmptyDirectory() {
        DirectoryInodeBusiness d = fs.createDirectory();
        root.addEntry("folder", d);

        boolean result = rmdir.rmdir("folder");
        assertTrue(result);
        assertNull(root.getEntry("folder"));
    }

    @Test
    void testRemoveEmptyDirectoryAbsolutePath() {
        DirectoryInodeBusiness d = fs.createDirectory();
        root.addEntry("abc", d);

        boolean result = rmdir.rmdir("/abc");
        assertTrue(result);
        assertNull(root.getEntry("abc"));
    }

    @Test
    void testRemoveDirNotExisting() {
        boolean result = rmdir.rmdir("doesNotExist");
        assertFalse(result);
    }

    @Test
    void testRemoveFileShouldFail() {
        FileInodeBusiness file = fs.createFile();
        root.addEntry("file.txt", file);

        boolean result = rmdir.rmdir("file.txt");
        assertFalse(result);
        assertNotNull(root.getEntry("file.txt"));
    }

    @Test
    void testRemoveNonEmptyDirectory() {
        DirectoryInodeBusiness d = fs.createDirectory();
        root.addEntry("folder", d);

        FileInodeBusiness file = fs.createFile();
        d.addEntry("inner.txt", file);

        boolean result = rmdir.rmdir("folder");
        assertFalse(result);

        assertNotNull(root.getEntry("folder"));
    }

    @Test
    void testRemoveRootShouldFail() {
        boolean result = rmdir.rmdir("/");
        assertFalse(result);
        assertEquals(root, fs.getRoot());
    }

    @Test
    void testRemoveNestedDirectoryRelative() {
        DirectoryInodeBusiness a = fs.createDirectory();
        DirectoryInodeBusiness b = fs.createDirectory();

        root.addEntry("a", a);
        a.addEntry("b", b);

        boolean result = rmdir.rmdir("a/b");
        assertTrue(result);
        assertNull(a.getEntry("b"));
    }

    @Test
    void testRemoveNestedDirectoryAbsolute() {
        DirectoryInodeBusiness a = fs.createDirectory();
        DirectoryInodeBusiness b = fs.createDirectory();

        root.addEntry("dir", a);
        a.addEntry("sub", b);

        boolean result = rmdir.rmdir("/dir/sub");
        assertTrue(result);
        assertNull(a.getEntry("sub"));
    }

    @Test
    void testRemoveUsingTrailingSlash() {
        DirectoryInodeBusiness d = fs.createDirectory();
        root.addEntry("temp", d);

        boolean result = rmdir.rmdir("temp/");
        assertTrue(result);
        assertNull(root.getEntry("temp"));
    }

    @Test
    void testRemoveFromAnotherWorkingDirectory() {
        DirectoryInodeBusiness home = fs.createDirectory();
        root.addEntry("home", home);

        DirectoryInodeBusiness docs = fs.createDirectory();
        home.addEntry("docs", docs);

        fs.setCurrentWorkingDirectory(home);
        fs.setCurrentWorkingDirectoryPath("/home");

        boolean result = rmdir.rmdir("docs");
        assertTrue(result);
        assertNull(home.getEntry("docs"));
    }
}
