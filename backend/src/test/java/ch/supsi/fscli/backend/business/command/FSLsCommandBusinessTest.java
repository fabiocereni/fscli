package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.FSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.IFSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSLsCommandBusinessTest {

    private IFSLsCommandBusiness lsCommand;
    private FileSystem fs;
    private DirectoryInodeBusiness root;

    @BeforeEach
    void setUp() {
        fs = new FileSystem();

        lsCommand = new FSLsCommandBusiness(fs, new PathSolver(fs));
        root = fs.getRoot();

        fs.setCurrentWorkingDirectory(root);
    }

    @Test
    void testLsCurrentDirectoryEmpty() {
        CommandResult result = lsCommand.ls(null, false);

        assertNull(result, "Se la directory è vuota, ls ritorna null");
    }

    @Test
    void testLsCurrentDirectoryWithContent() {
        root.addEntry("fileA", fs.createFile());
        root.addEntry("dirB", fs.createDirectory());

        CommandResult result = lsCommand.ls(null, false);

        assertNotNull(result);
        assertFalse(result.isTranslatable());

        String content = result.getContent();
        assertTrue(content.contains("fileA"));
        assertTrue(content.contains("dirB"));
        assertFalse(content.contains("["), "Senza -i non mostra gli ID");
    }

    @Test
    void testLsWithInodeOption() {
        root.addEntry("testFile", fs.createFile());

        CommandResult result = lsCommand.ls(null, true);

        assertNotNull(result);
        assertTrue(result.getContent().contains("testFile ["));
    }

    @Test
    void testLsSpecificPath() {
        DirectoryInodeBusiness subDir = fs.createDirectory();
        root.addEntry("documents", subDir);
        subDir.addEntry("notes.txt", fs.createFile());

        CommandResult result = lsCommand.ls("documents", false);

        assertNotNull(result);
        assertFalse(result.isTranslatable());
        assertTrue(result.getContent().contains("notes.txt"));
    }

    @Test
    void testLsPathNotFound() {
        CommandResult result = lsCommand.ls("nonEsiste", false);

        assertNotNull(result);
        assertTrue(result.isTranslatable(), "Deve essere true per le label di errore");
        assertEquals("label.wrongLsUse2", result.getContent());
    }

    @Test
    void testLsOnFile() {
        root.addEntry("image.png", fs.createFile());

        CommandResult result = lsCommand.ls("image.png", false);

        assertNotNull(result);
        assertFalse(result.isTranslatable());
        assertEquals("image.png", result.getContent());
    }
}