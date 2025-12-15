package ch.supsi.fscli.backend.DAO.persistence;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FSDataReaderDAOTest {

    private IFSDataReaderDAO dao;

    @BeforeEach
    void setUp() {
        dao = new FSDataReaderDAO();
    }

    @Test
    void testReadFromAFile() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        String contentToWrite = "Hello, world!";
        Files.writeString(tempFile, contentToWrite);

        String contentRead = dao.readFromAFile(tempFile.toFile());

        assertEquals(contentToWrite, contentRead);

        Files.deleteIfExists(tempFile);
    }
}