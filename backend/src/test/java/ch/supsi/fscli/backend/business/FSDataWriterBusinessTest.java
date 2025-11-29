package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FSDataWriterBusinessTest {

    private Injector injector;
    private IFSDataWriterBusiness writer;
    private FileSystem fs;
    private Path tempFile;

    @BeforeEach
    void setup() throws IOException {

        injector = Guice.createInjector(new FileSystemModule());

        writer = injector.getInstance(IFSDataWriterBusiness.class);
        fs = injector.getInstance(FileSystem.class);

        DirectoryInodeBusiness root = fs.getRoot();
        DirectoryInodeBusiness documents = fs.createDirectory(root);
        root.addEntry("documents", documents);

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
