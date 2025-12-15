package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSTest {

    private DirectoryInodeBusiness directory;
    private FileInodeBusiness file;

    private FileSystem fileSystem;
    private DirectoryInodeBusiness root;

    @BeforeEach
    public void setup() {
        root = new DirectoryInodeBusiness(1L);

        fileSystem = new FileSystem();

        fileSystem.setRoot(root);
        fileSystem.setCurrentWorkingDirectory(root);

        this.directory = fileSystem.createDirectory();
        this.file = fileSystem.createFile();

        DirectoryInodeBusiness cwd = fileSystem.getCurrentWorkingDirectory();
        cwd.addEntry("TestDir", directory);
        cwd.addEntry("TestFile", file);
    }

    @Test
    public void testDirectoryMethods() {
        DirectoryInodeBusiness cwd = fileSystem.getCurrentWorkingDirectory();

        assertTrue(directory.getId() > 0, "L'ID della directory dovrebbe essere positivo");

        Inode retrievedNode = cwd.getEntry("TestDir");
        assertNotNull(retrievedNode, "La directory 'TestDir' dovrebbe esistere nella CWD");
        assertEquals(directory, retrievedNode, "L'inode recuperato deve corrispondere a quello creato");

        assertEquals(InodeType.DIRECTORY, directory.getType(), "Il tipo deve essere DIRECTORY");
        assertTrue(directory instanceof DirectoryInodeBusiness);
    }

    @Test
    public void testFileMethods() {
        DirectoryInodeBusiness cwd = fileSystem.getCurrentWorkingDirectory();

        assertTrue(file.getId() > 0, "L'ID del file dovrebbe essere positivo");

        Inode retrievedFile = cwd.getEntry("TestFile");
        assertNotNull(retrievedFile, "Il file 'TestFile' dovrebbe esistere nella CWD");
        assertEquals(file, retrievedFile, "L'inode recuperato deve essere lo stesso oggetto");

        assertEquals(InodeType.FILE, file.getType(), "Il tipo deve essere FILE");
        assertTrue(file instanceof FileInodeBusiness);
    }
}