package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ln.IFSLnCommandBusiness;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import ch.supsi.fscli.backend.modules.FileSystemModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FSLnsCommandBusinessTest {

    private Injector injector;
    private IFSLnCommandBusiness ln;

    private FileSystem fs;
    private IFSStateBusiness state;

    private DirectoryInodeBusiness root;
    private FileInodeBusiness fileA;

    @BeforeEach
    void setUp() {

        injector = Guice.createInjector(new FileSystemModule());

        ln = injector.getInstance(IFSLnCommandBusiness.class);
        fs = injector.getInstance(FileSystem.class);
        state = injector.getInstance(IFSStateBusiness.class);

        // reset root del test
        root = fs.getRoot();
        state.setRoot(root);
        state.setCurrentWorkingDirectory(root);

        // crea file di test
        fileA = fs.createFile();
        root.addEntry("fileA", fileA);
    }

    @Test
    void testSoftLinkSuccess() throws Exception {
        boolean ok = ln.lns("fileA", "softA");
        assertTrue(ok);

        Inode inode = root.getEntry("softA");
        assertNotNull(inode);
        assertInstanceOf(FileInodeBusiness.class, inode);

        FileInodeBusiness soft = (FileInodeBusiness) inode;
        assertTrue(soft.isSoftLink());
        assertEquals("fileA", soft.getLinkPath());
        assertEquals(1, soft.getLinkCount());
    }

    @Test
    void testSoftLinkTargetDoesNotExist() {
        assertThrows(DirectoryNotFoundException.class,
                () -> ln.lns("nope", "softX"));
    }

    @Test
    void testSoftLinkParentDoesNotExist() {
        assertThrows(DirectoryNotFoundException.class,
                () -> ln.lns("fileA", "missing/soft"));
    }

    @Test
    void testSoftLinkAlreadyExists() {
        assertThrows(NodeAlreadyExistsException.class,
                () -> ln.lns("fileA", "fileA"));
    }

    @Test
    void testSoftLinkDoesNotFollowDirectoryRules() throws Exception {
        ln.lns("fileA", "alias");

        Inode link = root.getEntry("alias");
        assertNotNull(link);
        assertEquals(InodeType.FILE, link.getType());
    }
}
