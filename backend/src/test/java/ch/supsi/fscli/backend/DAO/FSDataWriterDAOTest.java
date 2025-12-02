package ch.supsi.fscli.backend.DAO;

import ch.supsi.fscli.backend.DAO.persistence.FSDataWriterDAO;
import ch.supsi.fscli.backend.DAO.persistence.IFSDataWriterDAO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FSDataWriterDAOTest {

    private final IFSDataWriterDAO fsDataWriterDAO = new FSDataWriterDAO();

    @Test
    void save() throws IOException {
        Path path = Path.of("target/test-saves/Test.json");
        Files.createDirectories(path.getParent());

        String toTest = "{\"testText\":\"Hello, World!\"}";

        fsDataWriterDAO.save(path, toTest);

        String saved = Files.readString(path);
        assertEquals(toTest, saved);

        Files.deleteIfExists(path);
    }
}
