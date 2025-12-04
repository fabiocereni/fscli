package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmfile.IFSRmfileCommandBusiness;
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

class FSRmfilecommandBusinessTest {

    private Injector injector;

    private IFSRmfileCommandBusiness rmFileCommand;
    private FileSystem fileSystem;

    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        rmFileCommand = injector.getInstance(IFSRmfileCommandBusiness.class);
        fileSystem     = injector.getInstance(FileSystem.class);
        IFSCreationBusiness creation = injector.getInstance(IFSCreationBusiness.class);

        // reset filesystem
        creation.newfs();

        root = fileSystem.getRoot();
        fileSystem.setCurrentWorkingDirectory(root);
        fileSystem.setCurrentWorkingDirectoryPath("/");
    }

    void createFileInRoot(String fileName) {
        FileInodeBusiness file = fileSystem.createFile();
        root.addEntry(fileName, file);
    }

    void createDirectoryInRoot(String dirName) {
        DirectoryInodeBusiness dir = fileSystem.createDirectory(root);
        root.addEntry(dirName, dir);
    }

    @Test
    void testRemoveExistingFileCurrentDirectory() {
        createFileInRoot("test.txt");

        assertNotNull(root.getEntry("test.txt"));

        boolean result = rmFileCommand.rmfile("test.txt");
        assertTrue(result);
        assertNull(root.getEntry("test.txt"));
    }

    @Test
    void testRemoveExistingFileAbsolutePath() {
        DirectoryInodeBusiness homeDir = fileSystem.createDirectory(root);
        root.addEntry("home", homeDir);

        FileInodeBusiness file = fileSystem.createFile();
        homeDir.addEntry("test1.txt", file);

        boolean result = rmFileCommand.rmfile("/home/test1.txt");
        assertTrue(result);
        assertNull(homeDir.getEntry("test1.txt"));
    }

    @Test
    void testRmFile_DecrementsLinkCount() {
        FileInodeBusiness file = fileSystem.createFile();
        root.addEntry("linkTest.txt", file);

        assertEquals(1, file.getLinkCount());
        rmFileCommand.rmfile("linkTest.txt");
        assertEquals(0, file.getLinkCount());
    }

    @Test
    void testRmFile_FileDoesNotExist() {
        assertFalse(rmFileCommand.rmfile("nonExistentFile.txt"));
    }

    @Test
    void testRmFileIfTryToRemoveDirectory() {
        createDirectoryInRoot("folder");
        assertFalse(rmFileCommand.rmfile("folder"));
        assertNotNull(root.getEntry("folder"));
    }

    @Test
    void testRmFileNullOrEmpty() {
        assertFalse(rmFileCommand.rmfile(null));
        assertFalse(rmFileCommand.rmfile(""));
        assertFalse(rmFileCommand.rmfile(" "));
    }

    @Test
    void testRmFileSpecialPath() {
        assertFalse(rmFileCommand.rmfile("/"));
        assertFalse(rmFileCommand.rmfile("."));
        assertFalse(rmFileCommand.rmfile(".."));
    }
}
