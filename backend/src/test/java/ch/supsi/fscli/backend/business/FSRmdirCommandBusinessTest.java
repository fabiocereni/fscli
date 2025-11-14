package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.*;
import ch.supsi.fscli.backend.business.FSCommands.FSRmdirCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSRmdirCommandBusinessTest {

    private FSRmdirCommandBusiness rmdir;
    private FSStateBusiness state;
    private DirectoryBusiness root;

    @BeforeEach
    void setup() {
        rmdir = FSRmdirCommandBusiness.getInstance();
        state = FSStateBusiness.getInstance();

        // Reset manuale (dipende da come è implementato FSStateBusiness)
        root = new DirectoryBusiness(null, "root");
        state.setCurrentWorkingDirectory(root);
    }

    @Test
    void testRmdirDirectoryDoesNotExist() {
        boolean result = rmdir.rmdir("missing");
        assertFalse(result, "rmdir should fail if directory does not exist");
    }

    @Test
    void testRmdirNameIsBlank() {
        assertFalse(rmdir.rmdir(""), "empty name must fail");
        assertFalse(rmdir.rmdir("   "), "blank name must fail");
        assertFalse(rmdir.rmdir(null), "null name must fail");
    }

    @Test
    void testRmdirOnFile() {
        INode file = new FileBusiness(root, "testfile");
        root.addContent(file);

        boolean result = rmdir.rmdir("testfile");

        assertFalse(result, "rmdir must fail on files");
        assertTrue(root.getContent().contains(file), "file must not be removed");
    }

    @Test
    void testRmdirOnNonEmptyDirectory() {
        DirectoryBusiness dir = new DirectoryBusiness(root, "docs");
        root.addContent(dir);

        // Add a child
        INode fileInside = new FileBusiness(dir, "inside");
        dir.addContent(fileInside);

        boolean result = rmdir.rmdir("docs");

        assertFalse(result, "rmdir must fail if directory is not empty");
        assertTrue(root.getContent().contains(dir), "directory must not be removed");
    }

    @Test
    void testRmdirSuccess() {
        DirectoryBusiness dir = new DirectoryBusiness(root, "empty");

        boolean result = rmdir.rmdir("empty");

        assertTrue(result, "rmdir must succeed for empty directory");
        assertFalse(root.getContent().contains(dir), "directory must be removed");
    }
}