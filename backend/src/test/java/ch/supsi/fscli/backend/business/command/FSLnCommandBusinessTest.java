package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Incubating;

import static org.junit.jupiter.api.Assertions.*;

class FSLnCommandBusinessTest {

    private FSLnCommandBusiness lnCommand;
    private FileSystem fileSystem;
    private DirectoryInodeBusiness root;
    private FileInodeBusiness targetFile;

    @BeforeEach
    void setUp() {
        fileSystem = new FileSystem();
        lnCommand = new FSLnCommandBusiness(fileSystem, new PathSolver(fileSystem));

        root = fileSystem.getRoot();

        fileSystem.setCurrentWorkingDirectory(root);
        targetFile = fileSystem.createFile();
        root.addEntry("targetFile", targetFile);
    }

    @Test
    void testLnSuccess() {
        String result = lnCommand.ln("targetFile", "hardLink");

        assertNull(result, "In caso di successo ln deve ritornare null");

        Inode linkNode = root.getEntry("hardLink");
        assertNotNull(linkNode);

        assertSame(targetFile, linkNode);
        assertEquals(2, targetFile.getLinkCount());
    }

    @Test
    void testLnTargetDoesNotExist() {
        String result = lnCommand.ln("nonEsiste", "link");

        assertEquals("label.wrongLnUse2", result);
    }

    @Test
    void testLnTargetIsDirectory() {
        DirectoryInodeBusiness dir = fileSystem.createDirectory();
        root.addEntry("myDir", dir);

        String result = lnCommand.ln("myDir", "linkToDir");

        assertEquals("label.wrongLnUse3", result);
    }

    @Test
    void testLnNameAlreadyExists() {
        root.addEntry("esistente", fileSystem.createFile());

        String result = lnCommand.ln("targetFile", "esistente");

        assertEquals("label.wrongLnsUse4", result);
    }

    @Test
    void testLnsSuccess() {
        String result = lnCommand.lns("targetFile", "softLink");

        assertNull(result, "In caso di successo lns deve ritornare null");

        Inode node = root.getEntry("softLink");
        assertNotNull(node);
        assertTrue(node instanceof FileInodeBusiness);

        FileInodeBusiness softLink = (FileInodeBusiness) node;
        assertTrue(softLink.isSoftLink());
        assertEquals("targetFile", softLink.getLinkPath());
    }

    @Test
    void testLnsTargetDoesNotExist() {
        String result = lnCommand.lns("ghostFile", "link");

        assertEquals("label.wrongLnsUse2", result);
    }

    @Test
    void testLnsParentDoesNotExist() {
        String result = lnCommand.lns("targetFile", "cartellaFinta/link");

        assertEquals("label.wrongLnsUse3", result);
    }
}