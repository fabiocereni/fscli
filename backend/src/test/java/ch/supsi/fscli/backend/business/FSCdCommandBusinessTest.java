package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.cd.FSCdCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSCdCommandBusinessTest {

    private FSCdCommandBusiness cdBusiness;
    private FSStateBusiness stateBusiness; // Usiamo la classe concreta per i setter
    private PathSolver pathSolver;
    private FileSystem fs;

    private DirectoryInodeBusiness rootDir;
    private DirectoryInodeBusiness subDir;
    private FileInodeBusiness testFile;

    @BeforeEach
    void setUp() {
        // 1. Setup manuale delle dipendenze (Simuliamo quello che fa Guice)

        // Creiamo la root manualmente
        rootDir = new DirectoryInodeBusiness(1L);

        // Inizializziamo il FileSystem (accede al costruttore protected perché siamo nello stesso package)
        fs = new FileSystem(rootDir);

        // Inizializziamo lo State
        stateBusiness = new FSStateBusiness();
        stateBusiness.setRoot(rootDir);
        stateBusiness.setCurrentWorkingDirectory(rootDir);
        stateBusiness.setCurrentWorkingDirectoryPath("/"); // Fondamentale per la logica del path assoluto

        // Inizializziamo il PathSolver iniettando lo state
        pathSolver = new PathSolver(stateBusiness);

        // Inizializziamo il Command iniettando state e solver
        cdBusiness = new FSCdCommandBusiness(stateBusiness, pathSolver);

        // 2. Popoliamo il FileSystem per i test

        // Crea /documents
        // Nota: createDirectory richiede il parent nel tuo FileSystem.java
        subDir = fs.createDirectory(rootDir);
        rootDir.addEntry("documents", subDir);

        // Crea /file.txt
        testFile = fs.createFile();
        rootDir.addEntry("file.txt", testFile);
    }

    @Test
    void testCdSuccess() {
        // Act
        boolean result = cdBusiness.cd("documents");

        // Assert
        assertTrue(result);
        assertEquals(subDir, stateBusiness.getCurrentWorkingDirectory());
        // Verifichiamo anche che il path string sia aggiornato correttamente
        assertEquals("/documents", stateBusiness.getCurrentWorkingDirectoryPath());
    }

    @Test
    void testCdNotFound() {
        boolean result = cdBusiness.cd("foo");

        assertFalse(result);
        // Non deve essersi mosso
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
        assertEquals("/", stateBusiness.getCurrentWorkingDirectoryPath());
    }

    @Test
    void testCdToFile() {
        boolean result = cdBusiness.cd("file.txt");

        assertFalse(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
    }

    @Test
    void testCdToParent() {
        // Arrange: Simuliamo di essere già dentro /documents
        stateBusiness.setCurrentWorkingDirectory(subDir);
        // IMPORTANTE: Dobbiamo settare manualmente anche la stringa del path
        // perché la nuova logica del cd si basa su questa stringa per calcolare i ".."
        stateBusiness.setCurrentWorkingDirectoryPath("/documents");

        // Act
        boolean result = cdBusiness.cd("..");

        // Assert
        assertTrue(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory(), "Dovrebbe tornare alla root");
        assertEquals("/", stateBusiness.getCurrentWorkingDirectoryPath(), "Il path dovrebbe essere /");
    }

    @Test
    void testCdToRoot() {
        // Arrange: Simuliamo di essere dentro /documents
        stateBusiness.setCurrentWorkingDirectory(subDir);
        stateBusiness.setCurrentWorkingDirectoryPath("/documents");

        // Act
        boolean result = cdBusiness.cd("/");

        // Assert
        assertTrue(result);
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
        assertEquals("/", stateBusiness.getCurrentWorkingDirectoryPath());
    }

    @Test
    void testCdComplexPath() {
        // Test extra: navigazione complessa (dipende dalla logica di PathSolver + CD)
        // Crea /documents/work
        DirectoryInodeBusiness workDir = fs.createDirectory(subDir);
        subDir.addEntry("work", workDir);

        // Vai in work
        cdBusiness.cd("documents/work");
        assertEquals(workDir, stateBusiness.getCurrentWorkingDirectory());
        assertEquals("/documents/work", stateBusiness.getCurrentWorkingDirectoryPath());

        // Torna indietro di due livelli
        cdBusiness.cd("../..");
        assertEquals(rootDir, stateBusiness.getCurrentWorkingDirectory());
        assertEquals("/", stateBusiness.getCurrentWorkingDirectoryPath());
    }
}