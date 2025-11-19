package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.touch.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.touch.IFSTouchCommandBusiness;
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

        DirectoryBusiness home = new DirectoryBusiness(root, "home");
        DirectoryBusiness user = new DirectoryBusiness(home, "user");


        touchCommandBusiness = FSTouchCommandBusiness.getInstance();
        fsState = FSStateBusiness.getInstance();
        fsState.setCurrentWorkingDirectory(user);
    }

    @Test
    public void touchInSpecificPathTest() {
        String path = "/home/user/test.txt";
        boolean result = touchCommandBusiness.touch(path);
        assertTrue(result);

        Optional<INode> tmp = PathSolver.resolvePath(path);
        assertTrue(tmp.isPresent());

        Optional<INode> elem;

        if(tmp.get().getType().equals(NodeType.DIRECTORY)) {
            IDirectoryBusiness dir = (DirectoryBusiness) tmp.get();

            elem = dir.getContent().stream()
                    .filter(e -> e.getName().equals("test.txt"))
                    .findFirst();
        } else {
            elem = tmp;
        }


        assertTrue(elem.isPresent());
    }

    @Test
    public void touchInCurrentDirectoryTest() {
        boolean result = touchCommandBusiness.touch("file.txt");
        assertTrue(result);

        IDirectoryBusiness cwd = fsState.getCurrentWorkingDirectory();
        Optional<INode> elem = cwd.getContent().stream()
                .filter(e -> e.getName().equals("file.txt"))
                .findFirst();

        assertTrue(elem.isPresent());
    }

    @Test
    public void touchInvalidPathTest() {
        assertThrows(IllegalArgumentException.class, () -> touchCommandBusiness.touch("/invalid/path/fail.txt"));
    }
}
