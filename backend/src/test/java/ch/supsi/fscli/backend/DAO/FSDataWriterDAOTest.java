package ch.supsi.fscli.backend.DAO;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FSDataWriterDAOTest {

    private final IFSDataWriterDAO fsDataWriterDAO = FSDataWriterDAO.getInstance();

    private static final String TEST ;
    private static final Path PATH = Path.of("/home/simone/Scrivania/Test.json");

    static {
        TEST = "Hello, World!";
    }

    @Test
    void save() throws IOException {
        String toTest = "{\"testText\":\"Hello, World!\"}";

        if(Files.exists(PATH)) {
            try {
                Files.deleteIfExists(PATH);
            } catch (IOException e) {
                System.err.println("Error when deleting the file at: " + PATH);
            }

            this.fsDataWriterDAO.save(PATH, "Overwritten");
            String saved = Files.readString(PATH);
            assertEquals("Overwritten", saved);
            System.out.println(saved);

        } else {
            this.fsDataWriterDAO.save(PATH, toTest);
            String saved = Files.readString(PATH);
            assertEquals(toTest, saved);
            System.out.println(saved);
        }

        //Files.deleteIfExists(PATH);
    }
}