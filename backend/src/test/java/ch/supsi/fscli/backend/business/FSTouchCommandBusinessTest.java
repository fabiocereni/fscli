package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSTouchCommandBusinessTest {

    private Injector injector;
    private FSTouchCommandBusiness touch;
    private FileSystem fs;

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness sub;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        touch = injector.getInstance(FSTouchCommandBusiness.class);
        fs = injector.getInstance(FileSystem.class);

        // reset filesystem
        injector.getInstance(IFSCreationBusiness.class)
                .newfs();

        root = fs.getRoot();

        // build structure
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