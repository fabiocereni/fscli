package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FSDataWriterBusinessTest {

    private Path tempFile;

    @BeforeEach
    void setup() throws IOException {
        // Creiamo un file temporaneo
        tempFile = Files.createTempFile("fs_test_", ".json");

        // Prepariamo un filesystem minimale
        FileSystem fs = FileSystem.getInstance();
        DirectoryInodeBusiness root = fs.getRoot();
        DirectoryInodeBusiness documents = fs.createDirectory(root);
        root.addEntry("documents", documents);

        FileInodeBusiness file = fs.createFile();
        documents.addEntry("file.txt", file);
    }

    @AfterEach
    void cleanup() throws IOException {
        if (Files.exists(tempFile))
            Files.delete(tempFile);
    }

    @Test
    void testSaveRealFileSystem() throws IOException {
        // Act: esegue il vero salvataggio
        FSDataWriterBusiness.getInstance().save(tempFile);

        // 1) Verifica che il file esista
        assertTrue(Files.exists(tempFile), "Il file deve essere stato creato");

        // 2) Legge davvero il contenuto
        String json = Files.readString(tempFile);
        assertNotNull(json);
        assertFalse(json.isBlank());

        // 3) Verifica che il JSON contenga parti note
        assertTrue(json.contains("inodeType"), "Dovrebbe contenere il tipo polimorfico");
        assertTrue(json.contains("directory"), "Dovrebbe contenere entry directory");
        assertTrue(json.contains("file"), "Dovrebbe contenere almeno un file");
        assertTrue(json.contains("documents"), "La directory 'documents' deve essere serializzata");

        // 4) Verifica che la root sia presente
        assertTrue(json.contains("\"root\""), "La root deve essere presente nel JSON");
    }
}
