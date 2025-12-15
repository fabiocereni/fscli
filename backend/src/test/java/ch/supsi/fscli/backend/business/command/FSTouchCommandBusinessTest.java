package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.IFSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSTouchCommandBusinessTest {

    private IFSTouchCommandBusiness touch;
    private FileSystem fs;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness sub;

    @BeforeEach
    void setUp() {
        fs = new FileSystem();

        touch = new FSTouchCommandBusiness(fs, new PathSolver(fs));

        root = fs.getRoot();

        sub = new DirectoryInodeBusiness(200);
        root.addEntry("sub", sub);
    }

    @Test
    void testTouchCreatesFileInRoot() {
        String result = touch.touch("newfile.txt");

        assertEquals(null, result);

        FileInodeBusiness file = (FileInodeBusiness) root.getEntry("newfile.txt");
        assertNotNull(file);
    }

    @Test
    void testTouchCreatesFileInSubfolder() {
        String result = touch.touch("sub/photo.png");

        assertEquals(null, result);

        FileInodeBusiness file = (FileInodeBusiness) sub.getEntry("photo.png");
        assertNotNull(file);
    }

    @Test
    void testTouchFailsWhenParentDoesNotExist() {
        String result = touch.touch("/ghost/file.txt");
        assertEquals("label.wrongTouchUse3", result);
    }

    @Test
    void testTouchFailsWhenFileExists() {
        touch.touch("sub/exist.txt");

        String result = touch.touch("sub/exist.txt");
        assertEquals("label.wrongTouchUse4", result);
    }

    @Test
    void testTouchNullArgument() {
        assertEquals("label.wrongTouchUse3", touch.touch(null));
    }
}