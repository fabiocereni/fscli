package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.MyFileNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LnCommandTest {

    private DirectoryBusiness root;
    private DirectoryBusiness dir1;
    private DirectoryBusiness dir2;
    private FileBusiness file;

    private FSLnCommandBusiness lnCommand;

    @BeforeEach
    void setup() {

        lnCommand = new FSLnCommandBusiness();

        // filesystem di test
        root = new DirectoryBusiness(null, "root");

        dir1 = new DirectoryBusiness(root, "dir1");
        root.addContent(dir1);

        dir2 = new DirectoryBusiness(root, "dir2");
        root.addContent(dir2);

        file = new FileBusiness(dir1, "test.txt");
        dir1.addContent(file);


    }

    @Test
    void testHardlinkCreatedCorrectly() throws Exception {
        assertTrue(lnCommand.ln("dir1/test.txt", "dir2/link.txt"));

        Optional<INode> link = PathSolver.resolvePath("dir2/link.txt");
        assertTrue(link.isPresent());
        assertEquals(NodeType.FILE, link.get().getType());
    }

    @Test
    void testHardlinkTargetNotFound() {
        assertThrows(MyFileNotFoundException.class, () ->
                lnCommand.ln("dir1/NOFILE", "dir2/link.txt")
        );
    }

    @Test
    void testHardlinkParentDirNotFound() {
        assertThrows(DirectoryNotFoundException.class, () ->
                lnCommand.ln("dir1/test.txt", "missingDir/link.txt")
        );
    }

    @Test
    void testHardlinkAlreadyExists() throws Exception {
        // create file in dir2 with name link.txt
        FileBusiness already = new FileBusiness(dir2, "link.txt");
        dir2.addContent(already);

        assertThrows(NodeAlreadyExistsException.class, () ->
                lnCommand.ln("dir1/test.txt", "dir2/link.txt")
        );
    }


    @Test
    void testHardlinkRelativePath() throws Exception {
        FSStateBusiness.getInstance().setCurrentWorkingDirectory(dir2);

        lnCommand.ln("../dir1/test.txt", "myHardLink.txt");

        assertTrue(PathSolver.resolvePath("dir2/myHardLink.txt").isPresent());
    }
}
