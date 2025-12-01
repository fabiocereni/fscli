package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.DAO.FSDataReaderDAO;
import ch.supsi.fscli.backend.DAO.FSDataWriterDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FSReaderTest {

    private FileSystem fileSystem;
    private FSStateBusiness fsState;
    private FSDataReaderBusiness readerBusiness;
    private FSDataWriterBusiness writerBusiness;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        // --- SETUP IDENTICO A PRIMA ---
        DirectoryInodeBusiness root = new DirectoryInodeBusiness(1L);
        fileSystem = new FileSystem(root);
        fsState = new FSStateBusiness();
        fsState.setRoot(root);
        fsState.setCurrentWorkingDirectory(root);
        fsState.setCurrentWorkingDirectoryPath("/");

        FSDataReaderDAO readerDAO = new FSDataReaderDAO();
        FSDataWriterDAO writerDAO = new FSDataWriterDAO();

        readerBusiness = new FSDataReaderBusiness(fileSystem, fsState, readerDAO);
        writerBusiness = new FSDataWriterBusiness(fileSystem, fsState, writerDAO);
    }

    @Test
    void testBackendSaveAndRestore() {
        // --- 1. POPOLAMENTO ---
        System.out.println("Step 1: Popolamento...");
        DirectoryInodeBusiness root = fileSystem.getRoot();

        // Struttura: /Photos/Summer
        DirectoryInodeBusiness photosDir = fileSystem.createDirectory(root);
        root.addEntry("Photos", photosDir);
        photosDir.addEntry(".", photosDir);
        photosDir.addEntry("..", root);

        DirectoryInodeBusiness summerDir = fileSystem.createDirectory(photosDir);
        photosDir.addEntry("Summer", summerDir);
        summerDir.addEntry(".", summerDir);
        summerDir.addEntry("..", photosDir);

        // File: /Photos/Summer/img.jpg
        FileInodeBusiness imgFile = fileSystem.createFile();
        summerDir.addEntry("img.jpg", imgFile);

        long originalId = photosDir.getId();
        fsState.setCurrentWorkingDirectory(photosDir);
        fsState.setCurrentWorkingDirectoryPath("/Photos");

        // *** STAMPA PRIMA DEL SALVATAGGIO ***
        System.out.println("\n--- STRUTTURA ORIGINALE (In Memoria) ---");
        printFileSystemStructure(root, "");
        System.out.println("----------------------------------------\n");


        // --- 2. SALVATAGGIO ---
        System.out.println("Step 2: Salvataggio...");
        Path backupFile = tempDir.resolve("visual_test.json");
        writerBusiness.save(backupFile);


        // --- 3. RESET ---
        System.out.println("Step 3: Cancellazione memoria...");

        // 1. Cancella l'indice (quello che facevi già)
        fileSystem.getInodeTable().clear();

        // 2. CORREZIONE: Svuota fisicamente la Root dai suoi figli!
        // Altrimenti FSState mantiene i riferimenti agli oggetti vecchi
        fsState.getRoot().getEntries().clear();

        // 3. Ripristina i link base obbligatori per la root (. e ..)
        // (Come se fosse un filesystem appena formattato)
        fsState.getRoot().addEntry(".", fsState.getRoot());
        fsState.getRoot().addEntry("..", fsState.getRoot());

        // 4. Resetta la posizione dell'utente (CWD) alla root
        fsState.setCurrentWorkingDirectory(fsState.getRoot());
        fsState.setCurrentWorkingDirectoryPath("/");

        // Ora questo assert ha senso sia logicamente che visivamente
        assertNull(fileSystem.getInode(originalId));

        // *** STAMPA DOPO LA CANCELLAZIONE DELLA MEMORIA ***
        System.out.println("\n--- STRUTTURA CANCELLATA ---");
        printFileSystemStructure(fsState.getRoot(), "");
        System.out.println("-------------------------------------\n");


        // --- 4. RESTORE ---
        System.out.println("Step 4: Restore...");
        readerBusiness.reader(backupFile.toFile());


        // --- 5. VERIFICHE ---
        System.out.println("Step 5: Verifiche...");

        DirectoryInodeBusiness loadedRoot = fsState.getRoot();
        assertNotNull(loadedRoot);
        assertTrue(fileSystem.getInodeTable().containsKey(originalId));

        // *** STAMPA DOPO IL CARICAMENTO ***
        System.out.println("\n--- STRUTTURA CARICATA (Dal JSON) ---");
        printFileSystemStructure(fsState.getRoot(), "");
        System.out.println("-------------------------------------\n");
    }

    // ==========================================
    // HELPER PER STAMPARE L'ALBERO (Ricorsivo)
    // ==========================================
    private void printFileSystemStructure(DirectoryInodeBusiness dir, String indent) {
        // Se è la root e l'indentazione è vuota, stampiamo "/"
        if (indent.isEmpty()) {
            System.out.println("/ (ID: " + dir.getId() + ")");
        }

        for (Map.Entry<String, Inode> entry : dir.getEntries().entrySet()) {
            String name = entry.getKey();
            Inode node = entry.getValue();

            // IMPORTANTE: Saltiamo . e .. per evitare loop infiniti di stampa
            if (name.equals(".") || name.equals("..")) {
                continue;
            }

            if (node instanceof DirectoryInodeBusiness subDir) {
                System.out.println(indent + "├── " + name + "/ (ID: " + subDir.getId() + ")");
                // Chiamata ricorsiva per scendere nel livello successivo
                printFileSystemStructure(subDir, indent + "│   ");
            } else if (node instanceof FileInodeBusiness file) {
                System.out.println(indent + "├── " + name + " (ID: " + file.getId() + ")");
            }
        }
    }
}