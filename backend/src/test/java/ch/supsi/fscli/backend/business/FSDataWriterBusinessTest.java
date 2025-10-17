package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FSDataWriterBusinessTest {

    private final IFSDataWriterBusiness ifsDataWriterBusiness = FSDataWriterBusiness.getInstance();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH");
    String fileName = LocalDateTime.now().format(formatter) + ".json";
    Path path = Paths.get(System.getProperty("user.home"), "FileSystem Simulator", "Saved", fileName);


    public static class TestSerialization extends AbstractBusiness {

        private final String testText = "Hello, World!";

        public String getTestText() {
            return testText;
        }
    }

    @Test
    void defaultPathSaveTest() throws IOException {
        String toCompare = "{\"testText\":\"Hello, World!\"}";

        ifsDataWriterBusiness.save(new TestSerialization());

        String saved = Files.readString(path);
        assertEquals(toCompare, saved);

//        Files.deleteIfExists(path);
//        Files.deleteIfExists(path);
    }

    @Test
    void saveAs() throws IOException {
        String toCompare = "{\"testText\":\"Hello, World!\"}";

        ifsDataWriterBusiness.save(Path.of("/home/simone/Scrivania/test.txt"),new TestSerialization());

        String saved = Files.readString(path);
        assertEquals(toCompare, saved);
    }
}