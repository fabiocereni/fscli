package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.DAO.persistence.FSDataReaderDAO;
import ch.supsi.fscli.backend.DAO.persistence.FSDataWriterDAO;
import ch.supsi.fscli.backend.business.persistence.FSDataReaderBusiness;
import ch.supsi.fscli.backend.business.persistence.FSDataWriterBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import com.google.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FSReaderTest {
    private FileSystem fileSystem;
    @Inject
    private FSDataReaderBusiness readerBusiness;
    @Inject
    private FSDataWriterBusiness writerBusiness;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        DirectoryInodeBusiness root = new DirectoryInodeBusiness(1L);
        fileSystem = new FileSystem();
        fileSystem.setRoot(root);
        fileSystem.setCurrentWorkingDirectory(root);
        fileSystem.setCurrentWorkingDirectoryPath("/");

        FSDataReaderDAO readerDAO = new FSDataReaderDAO();
        FSDataWriterDAO writerDAO = new FSDataWriterDAO();

        readerBusiness = new FSDataReaderBusiness(fileSystem, readerDAO);
        writerBusiness = new FSDataWriterBusiness(fileSystem, writerDAO);
    }

    @Test
    void testBackendSaveAndRestore() {
        System.out.println("Step 1: Popolamento...");
        DirectoryInodeBusiness root = fileSystem.getRoot();

        DirectoryInodeBusiness photosDir = fileSystem.createDirectory();
        root.addEntry("Photos", photosDir);
        photosDir.addEntry(".", photosDir);
        photosDir.addEntry("..", root);

        DirectoryInodeBusiness summerDir = fileSystem.createDirectory();
        photosDir.addEntry("Summer", summerDir);
        summerDir.addEntry(".", summerDir);
        summerDir.addEntry("..", photosDir);

        FileInodeBusiness imgFile = fileSystem.createFile();
        summerDir.addEntry("img.jpg", imgFile);

        long originalId = photosDir.getId();
        fileSystem.setCurrentWorkingDirectory(photosDir);
        fileSystem.setCurrentWorkingDirectoryPath("/Photos");

        System.out.println("\n--- STRUTTURA ORIGINALE (In Memoria) ---");
        printFileSystemStructure(root, "");
        System.out.println("----------------------------------------\n");

        System.out.println("Step 2: Salvataggio...");
        Path backupFile = tempDir.resolve("visual_test.json");
        writerBusiness.save(backupFile);

        System.out.println("Step 3: Cancellazione memoria...");

        fileSystem.getInodeTable().clear();

        fileSystem.getRoot().getEntries().clear();

        fileSystem.getRoot().addEntry(".", fileSystem.getRoot());
        fileSystem.getRoot().addEntry("..", fileSystem.getRoot());

        fileSystem.setCurrentWorkingDirectory(fileSystem.getRoot());
        fileSystem.setCurrentWorkingDirectoryPath("/");

        assertNull(fileSystem.getInode(originalId));

        System.out.println("\n--- STRUTTURA CANCELLATA ---");
        printFileSystemStructure(fileSystem.getRoot(), "");
        System.out.println("-------------------------------------\n");

        System.out.println("Step 4: Restore...");
        readerBusiness.reader(backupFile.toFile());

        System.out.println("Step 5: Verifiche...");

        DirectoryInodeBusiness loadedRoot = fileSystem.getRoot();
        assertNotNull(loadedRoot);
        assertTrue(fileSystem.getInodeTable().containsKey(originalId));

        System.out.println("\n--- STRUTTURA CARICATA (Dal JSON) ---");
        printFileSystemStructure(fileSystem.getRoot(), "");
        System.out.println("-------------------------------------\n");
    }

    private void printFileSystemStructure(DirectoryInodeBusiness dir, String indent) {
        if (indent.isEmpty()) {
            System.out.println("/ (ID: " + dir.getId() + ")");
        }

        for (Map.Entry<String, Inode> entry : dir.getEntries().entrySet()) {
            String name = entry.getKey();
            Inode node = entry.getValue();

            if (name.equals(".") || name.equals("..")) {
                continue;
            }

            if (node instanceof DirectoryInodeBusiness subDir) {
                System.out.println(indent + "├── " + name + "/ (ID: " + subDir.getId() + ")");
                printFileSystemStructure(subDir, indent + "│   ");
            } else if (node instanceof FileInodeBusiness file) {
                System.out.println(indent + "├── " + name + " (ID: " + file.getId() + ")");
            }
        }
    }
}