package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
public class LnsCommandTest {

    private DirectoryBusiness root = new DirectoryBusiness(null, "root");
    private DirectoryBusiness dir1;
    private DirectoryBusiness dir2;
    private FileBusiness file;

    private FSLnCommandBusiness lnCommand;

    @BeforeEach
    void setup() {

        lnCommand = new FSLnCommandBusiness();

        dir1 = new DirectoryBusiness(root, "dir1");

        dir2 = new DirectoryBusiness(root, "dir2");

        file = new FileBusiness(dir1, "test.txt");

    }

    //@Test
    void testSoftlinkCreatedCorrectly() throws Exception {
        assertTrue(lnCommand.lns("dir1/test.txt", "dir2/slink.txt"));

        Optional<INode> slink = PathSolver.resolvePath("dir2/slink.txt");
        assertTrue(slink.isPresent());

        FileBusiness fb = (FileBusiness) slink.get();
        assertTrue(fb.isSoftLink());
        assertEquals("dir1/test.txt", fb.getLinkPath());
    }

    //@Test
    void testSoftlinkTargetNotFound() {
        assertThrows(DirectoryNotFoundException.class, () ->
                lnCommand.lns("dir1/NOFILE", "dir2/slink.txt")
        );
    }

    //@Test
    void testSoftlinkParentDirNotFound() {
        assertThrows(DirectoryNotFoundException.class, () ->
                lnCommand.lns("dir1/test.txt", "missingDir/link.txt")
        );
    }

    //@Test
    void testSoftlinkAlreadyExists() throws Exception {
        FileBusiness exists = new FileBusiness(dir2, "slink.txt");
        dir2.addContent(exists);

        assertThrows(NodeAlreadyExistsException.class, () ->
                lnCommand.lns("dir1/test.txt", "dir2/slink.txt")
        );
    }

    //@Test
    void testSoftlinkRelativePath() throws Exception {
        FSStateBusiness.getInstance().setCurrentWorkingDirectory(dir2);

        lnCommand.lns("../dir1/test.txt", "mysym.txt");

        assertTrue(PathSolver.resolvePath("dir2/mysym.txt").isPresent());
    }


    @Test
    void extractParentDirectory() {

        String path = "/dir1/dir2";

        int lastIndex = path.lastIndexOf("/");
        String parentPath = path.substring(0, lastIndex);
        assertEquals("/dir1", parentPath);
        assertTrue(PathSolver.resolvePath(parentPath).isPresent());

    }


    @Test
    void extractRelativePath() {
        String path;

        if(!path.startsWith("/"))
            System.out.println("relativePath");
    }

}
