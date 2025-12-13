package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSRmdirCommandBusinessTest {

    private Injector injector;

    private IFSRmdirCommandBusiness rmdir;
    private FileSystem fs;
    private IFSCreationBusiness creation;

    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        rmdir = injector.getInstance(IFSRmdirCommandBusiness.class);
        fs = injector.getInstance(FileSystem.class);
        creation = injector.getInstance(IFSCreationBusiness.class);

        // Reset completo del filesystem
        creation.newfs();

        root = fs.getRoot();
        assertNotNull(root);

        fs.setCurrentWorkingDirectory(root);
        fs.setCurrentWorkingDirectoryPath("/");
    }

    @Test
    void testRemoveEmptyDirectory() {
        DirectoryInodeBusiness d = fs.createDirectory(root);
        root.addEntry("folder", d);

        boolean result = rmdir.rmdir("folder");
        assertTrue(result);
        assertNull(root.getEntry("folder"));
    }

    @Test
    void testRemoveEmptyDirectoryAbsolutePath() {
        DirectoryInodeBusiness d = fs.createDirectory(root);
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
        DirectoryInodeBusiness d = fs.createDirectory(root);
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
        DirectoryInodeBusiness a = fs.createDirectory(root);
        DirectoryInodeBusiness b = fs.createDirectory(a);

        root.addEntry("a", a);
        a.addEntry("b", b);

        boolean result = rmdir.rmdir("a/b");
        assertTrue(result);
        assertNull(a.getEntry("b"));
    }

    @Test
    void testRemoveNestedDirectoryAbsolute() {
        DirectoryInodeBusiness a = fs.createDirectory(root);
        DirectoryInodeBusiness b = fs.createDirectory(a);

        root.addEntry("dir", a);
        a.addEntry("sub", b);

        boolean result = rmdir.rmdir("/dir/sub");
        assertTrue(result);
        assertNull(a.getEntry("sub"));
    }

    @Test
    void testRemoveUsingTrailingSlash() {
        DirectoryInodeBusiness d = fs.createDirectory(root);
        root.addEntry("temp", d);

        boolean result = rmdir.rmdir("temp/");
        assertTrue(result);
        assertNull(root.getEntry("temp"));
    }

    @Test
    void testRemoveFromAnotherWorkingDirectory() {
        DirectoryInodeBusiness home = fs.createDirectory(root);
        root.addEntry("home", home);

        DirectoryInodeBusiness docs = fs.createDirectory(home);
        home.addEntry("docs", docs);

        fs.setCurrentWorkingDirectory(home);
        fs.setCurrentWorkingDirectoryPath("/home");

        boolean result = rmdir.rmdir("docs");
        assertTrue(result);
        assertNull(home.getEntry("docs"));
    }
}
