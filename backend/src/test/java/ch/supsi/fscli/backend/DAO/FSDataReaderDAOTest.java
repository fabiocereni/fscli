package ch.supsi.fscli.backend.DAO;

import ch.supsi.fscli.backend.DAO.persistence.FSDataReaderDAO;
import ch.supsi.fscli.backend.DAO.persistence.IFSDataReaderDAO;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.Properties;

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
        Files.writeString(tempFile, "Hello, world!");

        String content = dao.readFromAFile(tempFile.toFile());

        assertTrue(content.contains("Hello, world!"));
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testGetPreferencesFileNotExists() {
        Path fakePrefs = Paths.get(System.getProperty("user.home"),
                "filesystem_simulator", "user_preferences", "preferences.properties");

        try {
            Files.deleteIfExists(fakePrefs);
        } catch (IOException ignored) {}

        Properties props = dao.getPreferences();
        assertTrue(props.isEmpty(), "Properties devono essere vuote se il file non esiste");
    }

    @Test
    void testGetPreferencesFileExists() throws IOException {
        Path prefsDir = Paths.get(System.getProperty("user.home"),
                "filesystem_simulator", "user_preferences");
        Files.createDirectories(prefsDir);

        Path prefsFile = prefsDir.resolve("preferences.properties");
        Files.writeString(prefsFile, "test=test\nfontsize=14");

        Properties props = dao.getPreferences();

        assertEquals("test", props.getProperty("test"));
        assertEquals("14", props.getProperty("fontsize"));

        Files.deleteIfExists(prefsFile);
    }
}
