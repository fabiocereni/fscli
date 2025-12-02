package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.state.FSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSTest {

    private DirectoryInodeBusiness directory;
    private FileInodeBusiness file;

    // Dipendenze da iniettare/creare manualmente
    private FileSystem fileSystem;
    private IFSStateBusiness ifsStateBusiness;
    private DirectoryInodeBusiness root;

    @BeforeEach
    public void setup() {
        // 1. Setup manuale delle dipendenze per isolare il test
        root = new DirectoryInodeBusiness(1L); // Creiamo la root (id 1)

        fileSystem = new FileSystem(root); // Iniettiamo la root nel FS

        ifsStateBusiness = new FSStateBusiness();
        ifsStateBusiness.setRoot(root);
        ifsStateBusiness.setCurrentWorkingDirectory(root);

        // 2. Creazione entità per il test
        // createDirectory richiede il genitore come argomento
        this.directory = fileSystem.createDirectory(root);
        this.file = fileSystem.createFile();

        // 3. Collegamento manuale alla CWD (Root)
        // FileSystem crea l'oggetto, ma dobbiamo aggiungerlo noi alla directory corrente
        DirectoryInodeBusiness cwd = ifsStateBusiness.getCurrentWorkingDirectory();
        cwd.addEntry("TestDir", directory);
        cwd.addEntry("TestFile", file);
    }

    @Test
    public void testDirectoryMethods() {
        DirectoryInodeBusiness cwd = ifsStateBusiness.getCurrentWorkingDirectory();

        // 1) Verifica ID (deve essere > 0)
        assertTrue(directory.getId() > 0, "L'ID della directory dovrebbe essere positivo");

        // 2) Verificare che la CWD contenga la directory con il nome corretto
        Inode retrievedNode = cwd.getEntry("TestDir");
        assertNotNull(retrievedNode, "La directory 'TestDir' dovrebbe esistere nella CWD");
        assertEquals(directory, retrievedNode, "L'inode recuperato deve corrispondere a quello creato");

        // 3) Verifica Tipo
        assertEquals(InodeType.DIRECTORY, directory.getType(), "Il tipo deve essere DIRECTORY");
        assertTrue(directory instanceof DirectoryInodeBusiness);
    }

    @Test
    public void testFileMethods() {
        DirectoryInodeBusiness cwd = ifsStateBusiness.getCurrentWorkingDirectory();

        // 1) Verifica ID
        assertTrue(file.getId() > 0, "L'ID del file dovrebbe essere positivo");

        // 2) Verificare nome e recupero
        Inode retrievedFile = cwd.getEntry("TestFile");
        assertNotNull(retrievedFile, "Il file 'TestFile' dovrebbe esistere nella CWD");
        assertEquals(file, retrievedFile, "L'inode recuperato deve essere lo stesso oggetto");

        // 3) Verifica Tipo
        assertEquals(InodeType.FILE, file.getType(), "Il tipo deve essere FILE");
        assertTrue(file instanceof FileInodeBusiness);
    }
}