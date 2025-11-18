package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ls.FSLsiCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLsiCommandBusinessTest {

    private FSLsiCommandBusiness lsiCommand;
    private FSStateBusiness stateBusiness;
    private DirectoryBusiness rootDir;
    private DirectoryBusiness subDir;
    private FileBusiness file;

    @BeforeEach
    void setUp() {
        lsiCommand = FSLsiCommandBusiness.getInstance();
        stateBusiness = FSStateBusiness.getInstance();

        // reset state
        rootDir = new DirectoryBusiness(null, "root");
        stateBusiness.setRoot(rootDir);
        stateBusiness.setCurrentWorkingDirectory(rootDir);

        subDir = new DirectoryBusiness(rootDir, "subdir");
        file = new FileBusiness(rootDir, "file.txt");
    }

    @Test
    void testLsiOnDirectory() {
        String result = lsiCommand.lsi("/");
        assertTrue(result.contains("subdir"));
        assertTrue(result.contains("file.txt"));
    }

    @Test
    void testLsiOnFile() {
        String result = lsiCommand.lsi("file.txt");
        assertEquals("That is not a directory", result);
    }

    @Test
    void testLsiNonExisting() {
        String result = lsiCommand.lsi("nonexistent");
        assertEquals("Directory not found", result);
    }

    @Test
    void testLsiCurrentDirectoryWhenNull() {
        // pass null to list current working directory
        String result = lsiCommand.lsi(null);
        assertTrue(result.contains("subdir"));
        assertTrue(result.contains("file.txt"));
    }
}