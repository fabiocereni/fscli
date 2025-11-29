package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.mv.IFSMvCommandBusiness;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSMvCommandBusinessTest {

    private Injector injector;

    private IFSMvCommandBusiness mvCommand;
    private FileSystem fileSystem;
    private IFSStateBusiness fsState;
    private IFSCreationBusiness creation;

    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        mvCommand   = injector.getInstance(IFSMvCommandBusiness.class);
        fileSystem  = injector.getInstance(FileSystem.class);
        fsState     = injector.getInstance(IFSStateBusiness.class);
        creation    = injector.getInstance(IFSCreationBusiness.class);

        // reset completo del filesystem
        creation.newfs();

        root = fsState.getRoot();
        assertNotNull(root);

        fsState.setCurrentWorkingDirectory(root);

        // pulizia iniziale (solo se necessario)
        root.getEntries().clear();

        // aggiungiamo "." e ".." come nel vecchio test
        root.addEntry(".", root);
        root.addEntry("..", root);
    }

    private FileInodeBusiness createMockFile(String name, DirectoryInodeBusiness parent) {
        FileInodeBusiness file = fileSystem.createFile();
        parent.addEntry(name, file);
        return file;
    }

    private DirectoryInodeBusiness createMockDir(String name, DirectoryInodeBusiness parent) {
        DirectoryInodeBusiness dir = fileSystem.createDirectory(parent);
        parent.addEntry(name, dir);

        dir.addEntry(".", dir);
        dir.addEntry("..", parent);
        return dir;
    }

    @Test
    void testMvRenameFile() {
        FileInodeBusiness file = createMockFile("old.txt", root);
        long inodeId = file.getId();

        boolean result = mvCommand.mv("old.txt", "new.txt");

        assertTrue(result);
        assertNull(root.getEntry("old.txt"));
        assertNotNull(root.getEntry("new.txt"));
        assertEquals(inodeId, root.getEntry("new.txt").getId());
    }

    @Test
    void testMvRenameDirectory() {
        DirectoryInodeBusiness dir = createMockDir("oldDir", root);
        Inode oldParent = dir.getEntry("..");

        boolean result = mvCommand.mv("/oldDir", "/newDir");

        assertTrue(result);
        assertNull(root.getEntry("oldDir"));
        assertNotNull(root.getEntry("newDir"));
        assertEquals(dir, root.getEntry("newDir"));
        assertEquals(oldParent, dir.getEntry(".."));
    }

    @Test
    void testMvMoveFileToDirectory() {
        FileInodeBusiness file = createMockFile("doc.txt", root);
        DirectoryInodeBusiness folder = createMockDir("folder", root);

        boolean result = mvCommand.mv("doc.txt", "folder");

        assertTrue(result);
        assertNull(root.getEntry("doc.txt"));
        assertNotNull(folder.getEntry("doc.txt"));
        assertEquals(file, folder.getEntry("doc.txt"));
    }

    @Test
    void testMvMoveDirectoryUpdatesParentLink() {
        DirectoryInodeBusiness dirA = createMockDir("dirA", root);
        DirectoryInodeBusiness dirB = createMockDir("dirB", root);

        boolean result = mvCommand.mv("dirA", "dirB");

        assertTrue(result);

        assertNull(root.getEntry("dirA"));

        Inode movedDir = dirB.getEntry("dirA");
        assertNotNull(movedDir);

        DirectoryInodeBusiness castedMovedDir = (DirectoryInodeBusiness) movedDir;
        assertEquals(dirB, castedMovedDir.getEntry(".."));
    }

    @Test
    void testMvSourceNotFound() {
        assertFalse(mvCommand.mv("ghost.txt", "new.txt"));
    }

    @Test
    void testMvDestinationCollision() {
        createMockFile("a.txt", root);
        createMockFile("b.txt", root);

        boolean result = mvCommand.mv("a.txt", "b.txt");

        assertFalse(result);
        assertNotNull(root.getEntry("a.txt"));
    }

    @Test
    void testMv_MoveDirectoryIntoItself_ShouldFail() {
        DirectoryInodeBusiness parent = createMockDir("parent", root);

        boolean result = mvCommand.mv("parent", "parent");

        assertFalse(result);
    }

    @Test
    void testMvMoveParentIntoChildShouldFail() {
        DirectoryInodeBusiness parent = createMockDir("parent", root);
        createMockDir("child", parent);

        boolean result = mvCommand.mv("parent", "parent/child");

        assertFalse(result);
        assertNotNull(root.getEntry("parent"));
    }
}
