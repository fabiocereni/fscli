package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.DAO.persistence.FSDataWriterDAO;
import ch.supsi.fscli.backend.business.persistence.FSDataWriterBusiness;
import ch.supsi.fscli.backend.business.persistence.IFSDataWriterBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FSDataWriterBusinessTest {

    private IFSDataWriterBusiness writer;
    private FileSystem fs;
    private Path tempFile;

    @BeforeEach
    void setup() throws IOException {

        fs = new FileSystem();

        DirectoryInodeBusiness root = fs.getRoot();
        DirectoryInodeBusiness documents = fs.createDirectory();
        root.addEntry("documents", documents);

        FSDataWriterDAO writerDAO = new FSDataWriterDAO();

        writer = new FSDataWriterBusiness(fs, writerDAO);

        FileInodeBusiness file = fs.createFile();
        documents.addEntry("file.txt", file);

        tempFile = Files.createTempFile("fs_test_", ".json");
    }

    @AfterEach
    void cleanup() throws IOException {
        if (tempFile != null && Files.exists(tempFile))
            Files.delete(tempFile);
    }

    @Test
    void testSaveRealFileSystem() throws IOException {

        writer.save(tempFile);

        assertTrue(Files.exists(tempFile));

        String json = Files.readString(tempFile);
        assertNotNull(json);
        assertFalse(json.isBlank());

        assertTrue(json.contains("inodeType"));
        assertTrue(json.contains("directory"));
        assertTrue(json.contains("file"));
        assertTrue(json.contains("documents"));
        assertTrue(json.contains("\"root\""));
    }
}
