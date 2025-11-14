package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.IFSTouchCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FSTouchCommandBusinessTest {

    private IFSStateBusiness fsState;
    private IFSTouchCommandBusiness touchCommandBusiness;

    @BeforeEach
    void setup() {
        FSStateBusiness.getInstance().setRoot(new DirectoryBusiness(null, "root"));
        DirectoryBusiness root = FSStateBusiness.getInstance().getRoot();
        root.getContent().clear();
        DirectoryBusiness home = new DirectoryBusiness(root, "home");
        DirectoryBusiness user = new DirectoryBusiness(home, "user");

        root.addContent(home);
        home.addContent(user);

        touchCommandBusiness = FSTouchCommandBusiness.getInstance();
        fsState = FSStateBusiness.getInstance();
        fsState.setCurrentWorkingDirectory(user);
    }

    @Test
    public void touchInSpecificPathTest() {
        String path = "/home/user";
        boolean result = touchCommandBusiness.touch("test.txt", path);
        assertTrue(result);

        Optional<INode> tmp = PathSolver.resolvePath(path);
        assertTrue(tmp.isPresent());

        IDirectoryBusiness dir = (DirectoryBusiness) tmp.get();
        Optional<INode> elem = dir.getContent().stream()
                .filter(e -> e.getName().equals("test.txt"))
                .findFirst();

        assertTrue(elem.isPresent());
    }

    @Test
    public void touchInCurrentDirectoryTest() {
        boolean result = touchCommandBusiness.touch("file.txt", null);
        assertTrue(result);

        IDirectoryBusiness cwd = fsState.getCurrentWorkingDirectory();
        Optional<INode> elem = cwd.getContent().stream()
                .filter(e -> e.getName().equals("file.txt"))
                .findFirst();

        assertTrue(elem.isPresent());
    }

    @Test
    public void touchInvalidPathTest() {
        boolean result = touchCommandBusiness.touch("fail.txt", "/invalid/path");
        assertFalse(result);
    }
}
