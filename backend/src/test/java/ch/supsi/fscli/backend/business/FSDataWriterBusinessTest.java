package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FSDataWriterBusinessTest {

    private final IFSDataWriterBusiness ifsDataWriterBusiness = FSDataWriterBusiness.getInstance();

    private Path testDirectory;
    private Path testFile;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH");
    String fileName = LocalDateTime.now().format(formatter) + ".json";

    public static class TestSerialization extends AbstractBusiness {
        private final String testText = "Hello, World!";
        public String getTestText() {
            return testText;
        }
    }

    @BeforeEach
    void setup() throws IOException {
        testDirectory = Path.of("target/test-saves");
        Files.createDirectories(testDirectory);

        testFile = testDirectory.resolve(fileName);
    }

    @Test
    void defaultPathSaveTest() throws IOException {
        String toCompare = "{\"testText\":\"Hello, World!\"}";

        ifsDataWriterBusiness.save(testFile, new TestSerialization());

        String saved = Files.readString(testFile);
        assertEquals(toCompare, saved);

        Files.deleteIfExists(testFile);
    }

    @Test
    void saveAs() throws IOException {
        String toCompare = "{\"testText\":\"Hello, World!\"}";

        ifsDataWriterBusiness.save(testFile, new TestSerialization());

        String saved = Files.readString(testFile);
        assertEquals(toCompare, saved);

        Files.deleteIfExists(testFile);
    }
}
