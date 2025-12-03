package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSLnCommandBusinessTest {

    private Injector injector;

    private FSLnCommandBusiness lnCommand;
    private IFSStateBusiness state;
    private FileSystem fs;
    private PathSolver pathSolver;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness home;
    private DirectoryInodeBusiness user;
    private FileInodeBusiness fileA;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new FileSystemModule());

        lnCommand = injector.getInstance(FSLnCommandBusiness.class);
        state     = injector.getInstance(IFSStateBusiness.class);
        fs        = injector.getInstance(FileSystem.class);
        pathSolver = injector.getInstance(PathSolver.class);

        // reset filesystem
        injector.getInstance(IFSCreationBusiness.class).newfs();

        root = state.getRoot();

        // costruzione manuale albero
        home = new DirectoryInodeBusiness(200);
        user = new DirectoryInodeBusiness(300);

        fileA = fs.createFile();

        root.addEntry("home", home);
        home.addEntry("user", user);

        user.addEntry("fileA.txt", fileA);

        state.setCurrentWorkingDirectory(root);
    }

    // -----------------------------------------------------
    // HARD LINK TESTS
    // -----------------------------------------------------

    @Test
    void testHardLinkSuccess() {
        boolean result = lnCommand.ln("/home/user/fileA.txt", "/home/user/fileA_link.txt");

        assertTrue(result);

        Inode linked = user.getEntry("fileA_link.txt");
        assertNotNull(linked);
        assertEquals(linked, fileA);
        assertEquals(2, fileA.getLinkCount());
    }

    @Test
    void testHardLinkTargetNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> lnCommand.ln("/home/user/notExists.txt", "/home/user/x"));
        assertEquals("ln: target file does not exist", ex.getMessage());
    }

    @Test
    void testHardLinkTargetIsNotFile() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> lnCommand.ln("/home/user", "/home/user/x"));
        assertEquals("ln: target is not a file", ex.getMessage());
    }

    @Test
    void testHardLinkParentDirNotExists() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> lnCommand.ln("/home/user/fileA.txt", "/ghost/fileA_link.txt"));
        assertEquals("ln: parent directory does not exist", ex.getMessage());
    }

    @Test
    void testHardLinkNameAlreadyExists() {
        // fileA.txt exists
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> lnCommand.ln("/home/user/fileA.txt", "/home/user/fileA.txt"));
        assertEquals("ln: file with same name already exists", ex.getMessage());
    }


    // -----------------------------------------------------
    // SOFT LINK TESTS
    // -----------------------------------------------------

    @Test
    void testSoftLinkSuccess() {
        String msg = lnCommand.lns("/home/user/fileA.txt", "/home/user/linkSoft.txt");

        assertEquals("", msg);

        Inode soft = user.getEntry("linkSoft.txt");
        assertNotNull(soft);
        assertTrue(((FileInodeBusiness) soft).isSoftLink());
        assertEquals("/home/user/fileA.txt", ((FileInodeBusiness) soft).getLinkPath());
    }

    @Test
    void testSoftLinkTargetNotFound() {
        String msg = lnCommand.lns("/home/user/notFound.txt", "/home/user/x");
        assertEquals("label.wrongLnUse2", msg);
    }

    @Test
    void testSoftLinkParentNotFound() {
        String msg = lnCommand.lns("/home/user/fileA.txt", "/ghost/link.txt");
        assertEquals("label.wrongLnUse3", msg);
    }

    @Test
    void testSoftLinkNameAlreadyExists() {
        String msg = lnCommand.lns("/home/user/fileA.txt", "/home/user/fileA.txt");
        assertEquals("label.wrongLnUse4", msg);
    }
}