package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.mv.FSMvCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSMvCommandBusinessTest {

    private FSMvCommandBusiness mvBusiness;
    private IFSStateBusiness stateBusiness;
    private DirectoryBusiness rootDir;
    private DirectoryBusiness dirA;
    private DirectoryBusiness dirB;
    private FileBusiness fileA;
    private FileBusiness fileB;

    @BeforeEach
    void setUp() {
        mvBusiness = FSMvCommandBusiness.getInstance();
        stateBusiness = FSStateBusiness.getInstance();

        rootDir = new DirectoryBusiness(null, "root");
        fileA = new FileBusiness(rootDir, "fileA.txt");
        fileB = new FileBusiness(rootDir, "fileB.txt");
        dirA = new DirectoryBusiness(rootDir, "dirA");
        dirB = new DirectoryBusiness(rootDir, "dirB");

        stateBusiness.setRoot(rootDir);
        stateBusiness.setCurrentWorkingDirectory(rootDir);
    }

    @Test
    void testRenameFileSuccess() {
        boolean result = mvBusiness.mv("fileA.txt", "fileC.txt");

        assertTrue(result, "L'operazione di rinomina deve avere successo");
        assertEquals("fileC.txt", fileA.getName(), "Il nome del file deve essere cambiato");
        assertTrue(rootDir.getContent().contains(fileA), "Il file deve essere ancora nella root");
    }

    @Test
    void testRenameDirectorySuccess() {
        boolean result = mvBusiness.mv("dirA", "dirC");

        assertTrue(result, "L'operazione di rinomina deve avere successo");
        assertEquals("dirC", dirA.getName(), "Il nome della cartella deve essere cambiato");
        assertTrue(rootDir.getContent().contains(dirA), "La cartella deve essere ancora nella root");
    }

    @Test
    void testMoveFileIntoDirectorySuccess() {
        boolean result = mvBusiness.mv("fileA.txt", "dirA");

        assertTrue(result, "Lo spostamento del file deve avere successo");
        assertFalse(rootDir.getContent().contains(fileA), "Il file non deve più essere nella root");
        assertTrue(dirA.getContent().contains(fileA), "Il file deve essere ora in dirA");
        assertEquals(dirA, fileA.getParent(), "Il genitore del file deve essere dirA");
    }

    @Test
    void testMoveDirectoryIntoDirectorySuccess() {
        boolean result = mvBusiness.mv("dirA", "dirB");

        assertTrue(result, "Lo spostamento della cartella deve avere successo");
        assertFalse(rootDir.getContent().contains(dirA), "dirA non deve più essere nella root");
        assertTrue(dirB.getContent().contains(dirA), "dirA deve essere ora in dirB");
        assertEquals(dirB, dirA.getParent(), "Il genitore di dirA deve essere dirB");
    }

    @Test
    void testMoveSourceNotFound() {
        boolean result = mvBusiness.mv("file_inesistente.txt", "dirA");
        assertFalse(result, "Deve fallire se la sorgente non esiste");
    }

    @Test
    void testMoveDestinationIsFile() {
        boolean result = mvBusiness.mv("fileA.txt", "fileB.txt");

        assertFalse(result, "Deve fallire se la destinazione è un file");
        assertEquals("fileA.txt", fileA.getName(), "fileA non deve essere rinominato");
        assertEquals(rootDir, fileA.getParent(), "fileA deve rimanere nella root");
    }

    @Test
    void testMoveDirectoryIntoItself() {
        boolean result = mvBusiness.mv("dirA", "dirA");
        assertFalse(result, "Deve fallire se si sposta una cartella in sé stessa");
    }

    @Test
    void testMoveWithDestinationNameCollision() {
        FileBusiness fileCollisione = new FileBusiness(dirA, "fileA.txt");

        boolean result = mvBusiness.mv("fileA.txt", "dirA");

        assertFalse(result, "Deve fallire per collisione di nome");
        assertTrue(rootDir.getContent().contains(fileA), "Il fileA originale deve rimanere nella root");
        assertTrue(dirA.getContent().contains(fileCollisione), "dirA deve mantenere il suo file originale");
        assertEquals(1, dirA.getContent().size(), "dirA deve avere solo 1 file");
    }

    @Test
    void testMoveWithInvalidNames() {
        assertFalse(mvBusiness.mv("fileA.txt", null), "Destinazione null deve fallire");
        assertFalse(mvBusiness.mv(null, "dirA"), "Sorgente null deve fallire");
        assertFalse(mvBusiness.mv("fileA.txt", "  "), "Destinazione blank deve fallire");
    }
}