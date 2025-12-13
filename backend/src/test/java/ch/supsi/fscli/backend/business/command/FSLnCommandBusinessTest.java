package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Incubating;

import static org.junit.jupiter.api.Assertions.*;

class FSLnCommandBusinessTest {

    private FSLnCommandBusiness lnCommand;
    private FileSystem fileSystem;
    private DirectoryInodeBusiness root;
    private FileInodeBusiness targetFile;

    @BeforeEach
    void setUp() {


        fileSystem = new FileSystem();
        lnCommand = new FSLnCommandBusiness(fileSystem, new PathSolver(fileSystem));

        root = fileSystem.getRoot();

        // Setup CWD e file target
        fileSystem.setCurrentWorkingDirectory(root);
        targetFile = fileSystem.createFile();
        root.addEntry("targetFile", targetFile);
    }

    // --- TEST HARD LINK (ln) ---

    @Test
    void testLnSuccess() {
        // Esecuzione: ritorna String (null se successo)
        String result = lnCommand.ln("targetFile", "hardLink");

        // Verifica successo
        assertNull(result, "In caso di successo ln deve ritornare null");

        // Verifica effetto collaterale (link creato)
        Inode linkNode = root.getEntry("hardLink");
        assertNotNull(linkNode);

        // Verifica che sia un Hard Link (stesso oggetto, contatore incrementato)
        assertSame(targetFile, linkNode);
        assertEquals(2, targetFile.getLinkCount());
    }

    @Test
    void testLnTargetDoesNotExist() {
        String result = lnCommand.ln("nonEsiste", "link");

        // Verifica errore specifico
        assertEquals("label.wrongLnUse2", result);
    }

    @Test
    void testLnTargetIsDirectory() {
        // Creazione directory target
        DirectoryInodeBusiness dir = fileSystem.createDirectory();
        root.addEntry("myDir", dir);

        String result = lnCommand.ln("myDir", "linkToDir");

        // Hard link su directory non permesso
        assertEquals("label.wrongLnUse3", result);
    }

    @Test
    void testLnNameAlreadyExists() {
        // Creiamo un file che occupa già il nome destinazione
        root.addEntry("esistente", fileSystem.createFile());

        // Nota: Nel tuo codice ln() usa le label di lns() per parent e name exists (es. label.wrongLnsUse4)
        String result = lnCommand.ln("targetFile", "esistente");

        assertEquals("label.wrongLnsUse4", result);
    }

    // --- TEST SOFT LINK (lns) ---

    @Test
    void testLnsSuccess() {
        String result = lnCommand.lns("targetFile", "softLink");

        // Verifica successo
        assertNull(result, "In caso di successo lns deve ritornare null");

        // Verifica creazione soft link
        Inode node = root.getEntry("softLink");
        assertNotNull(node);
        assertTrue(node instanceof FileInodeBusiness);

        FileInodeBusiness softLink = (FileInodeBusiness) node;
        assertTrue(softLink.isSoftLink());
        assertEquals("targetFile", softLink.getLinkPath());
    }

    @Test
    void testLnsTargetDoesNotExist() {
        // La tua implementazione richiede che il target esista
        String result = lnCommand.lns("ghostFile", "link");

        assertEquals("label.wrongLnsUse2", result);
    }

    @Test
    void testLnsParentDoesNotExist() {
        String result = lnCommand.lns("targetFile", "cartellaFinta/link");

        assertEquals("label.wrongLnsUse3", result);
    }
}