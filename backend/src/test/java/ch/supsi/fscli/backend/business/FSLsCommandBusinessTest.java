package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.FSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.*;
import ch.supsi.fscli.backend.business.filesystem.state.*;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FSLsCommandBusinessTest {

    private Injector injector;

    private FSLsCommandBusiness ls;
    private FileSystem fs;
    private PathSolver solver;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness home;
    private FileInodeBusiness file;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        ls     = injector.getInstance(FSLsCommandBusiness.class);
        fs     = injector.getInstance(FileSystem.class);
        solver = injector.getInstance(PathSolver.class);

        injector.getInstance(IFSCreationBusiness.class).newfs();

        root = fs.getRoot();

        home = new DirectoryInodeBusiness(2);
        file = fs.createFile();

        root.addEntry("home", home);
        home.addEntry("file.txt", file);

        fs.setCurrentWorkingDirectory(root);
    }

    @Test
    void testLsOnRoot() {

        String output = ls.ls("/", false);

        assertTrue(output.contains("home"));
        assertFalse(output.contains("["));
    }

    @Test
    void testLsWithInodeIds() {

        String output = ls.ls("/", true);

        assertTrue(output.contains("home"));
        assertTrue(output.contains("["));
    }

    @Test
    void testLsOnFile() {

        String output = ls.ls("/home/file.txt", false);

        assertEquals("file.txt", output);
    }

    @Test
    void testLsOnMissingPath() {

        assertEquals("label.wrongLsUse2", ls.ls("/ghost", false));
    }

    @Test
    void testLsRelativePath() {

        fs.setCurrentWorkingDirectory(home);

        String output = ls.ls(".", false);

        assertTrue(output.contains("file.txt"));
    }
}