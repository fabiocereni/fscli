package ch.supsi.fscli.backend.business;


import ch.supsi.fscli.backend.business.FSCommands.mkdir.FSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmfile.FSRmfilecommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FSRmfilecommandBusinessTest {

    private FSRmfilecommandBusiness rmFileCommand;
    private FileSystem fileSystem;
    private FSStateBusiness fsState;
    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {
        rmFileCommand = FSRmfilecommandBusiness.getInstance();
        fileSystem = FileSystem.getInstance();
        fsState = FSStateBusiness.getInstance();

        root = fileSystem.getRoot();
        fsState.setRoot(root);
        fsState.setCurrentWorkingDirectory(root);
        fsState.setCurrentWorkingDirectoryPath("/");
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

        assertNotNull(root.getEntry("test.txt"), "File not created");

        boolean result = rmFileCommand.rmfile("test.txt");
        assertTrue(result, "File removed");
        assertNull(root.getEntry("test.txt"), "File not removed");
    }

    @Test
    void testRemoveExistingFileAbsolutePath() {
        DirectoryInodeBusiness homeDir = fileSystem.createDirectory(root);
        root.addEntry("home", homeDir);

        FileInodeBusiness file = fileSystem.createFile();
        homeDir.addEntry("test1.txt", file);

        boolean result = rmFileCommand.rmfile("/home/test1.txt");
        assertTrue(result, "File removed");
        assertNull(homeDir.getEntry("test1.txt"), "File not removed");
    }

    @Test
    void testRmFile_DecrementsLinkCount() {
        String fileName = "linkTest.txt";
        FileInodeBusiness file = fileSystem.createFile();
        root.addEntry(fileName, file);

        assertEquals(1, file.getLinkCount());
        rmFileCommand.rmfile(fileName);
        assertEquals(0, file.getLinkCount());
    }

    @Test
    void testRmFile_FileDoesNotExist() {
        boolean result = rmFileCommand.rmfile("nonExistentFile.txt");
        assertFalse(result);
    }

    @Test
    void testRmFileIfTryToRemoveDirectory() {
        createDirectoryInRoot("folder");
        boolean result = rmFileCommand.rmfile("folder");
        assertFalse(result);
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