package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.touch.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.touch.IFSTouchCommandBusiness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FSTouchCommandBusinessTest {

    private IFSStateBusiness fsState;
    private IFSTouchCommandBusiness touchCommandBusiness;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness home;
    private DirectoryInodeBusiness user;

    @BeforeEach
    void setup() {

        FSCreationBusiness.getInstance().newfs();
        fsState = FSStateBusiness.getInstance();

        // crea root
        root = FileSystem.getInstance().createDirectory();
        fsState.setRoot(root);

        // crea /home
        home = FileSystem.getInstance().createDirectory();
        root.addEntry("home", home);

        // crea /home/user
        user = FileSystem.getInstance().createDirectory();
        home.addEntry("user", user);

        // CWD = /home/user
        fsState.setCurrentWorkingDirectory(user);

        touchCommandBusiness = FSTouchCommandBusiness.getInstance();
    }

    @Test
    public void touchInSpecificPathTest() {
        String path = "/home/user/test.txt";

        boolean result = touchCommandBusiness.touch(path);
        assertTrue(result);

        Optional<Inode> resolved = PathSolver.resolvePath(path);
        assertTrue(resolved.isPresent());

        // verifico che il file sia realmente nella directory corretta
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory(path);
        assertNotNull(parent);

        Inode file = parent.getEntry("test.txt");
        assertNotNull(file);
    }

    @Test
    public void touchInCurrentDirectoryTest() {

        boolean result = touchCommandBusiness.touch("file.txt");
        assertTrue(result);

        DirectoryInodeBusiness cwd = fsState.getCurrentWorkingDirectory();
        Inode file = cwd.getEntry("file.txt");

        assertNotNull(file);
    }

    @Test
    public void touchInvalidPathTest() {

        assertThrows(IllegalArgumentException.class,
                () -> touchCommandBusiness.touch("/invalid/path/fail.txt"));
    }
}
