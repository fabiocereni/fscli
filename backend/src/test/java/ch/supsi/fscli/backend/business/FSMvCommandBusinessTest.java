package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.mv.FSMvCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSMvCommandBusinessTest {

    private FSMvCommandBusiness mvCommand;
    private FileSystem fileSystem;
    private FSStateBusiness fsState;
    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {
        mvCommand = FSMvCommandBusiness.getInstance();
        fileSystem = FileSystem.getInstance();
        fsState = FSStateBusiness.getInstance();
        root = fileSystem.getRoot();

        fsState.setRoot(root);
        fsState.setCurrentWorkingDirectory(root);
        root.getEntries().clear();

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
        assertNull(root.getEntry("oldDir"), "non deve esistere oldDir");
        assertNotNull(root.getEntry("newDir"), "deve esistere newDir");
        assertEquals(dir, root.getEntry("newDir"), "newDir deve essere la directory che era oldDir");
        assertEquals(oldParent, dir.getEntry(".."), "newDir deve avere come parent lo stesso di oldDir");
    }

    @Test
    void testMvMoveFileToDirectory() {
        FileInodeBusiness file = createMockFile("doc.txt", root);
        DirectoryInodeBusiness folder = createMockDir("folder", root);

        boolean result = mvCommand.mv("doc.txt", "folder");

        assertTrue(result);
        assertNull(root.getEntry("doc.txt"), "Non deve più essere nella root");
        assertNotNull(folder.getEntry("doc.txt"), "Deve essere dentro folder");
        assertEquals(file, folder.getEntry("doc.txt"));
    }

    @Test
    void testMvMoveDirectoryUpdatesParentLink() {
        DirectoryInodeBusiness dirA = createMockDir("dirA", root);
        DirectoryInodeBusiness dirB = createMockDir("dirB", root);

        //sposto dirA dentro dirB
        boolean result = mvCommand.mv("dirA", "dirB");

        assertTrue(result);

        // 1. dirA non è più in root
        assertNull(root.getEntry("dirA"));

        // 2. dirA è dentro dirB
        Inode movedDir = dirB.getEntry("dirA");
        assertNotNull(movedDir);

        // 3. VERIFICA CRITICA: il ".." di dirA deve ora puntare a dirB, non più a root
        DirectoryInodeBusiness castedMovedDir = (DirectoryInodeBusiness) movedDir;
        assertEquals(dirB, castedMovedDir.getEntry(".."), "Il parent (..) deve essere aggiornato");
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

        assertFalse(result, "Non si può spostare una cartella dentro se stessa");
    }

    @Test
    void testMvMoveParentIntoChildShouldFail() {
        DirectoryInodeBusiness parent = createMockDir("parent", root);
        createMockDir("child", parent);

        boolean result = mvCommand.mv("parent", "parent/child");

        assertFalse(result, "Non si può spostare un genitore dentro un suo discendente (ciclo)");

        assertNotNull(root.getEntry("parent"));
    }
}