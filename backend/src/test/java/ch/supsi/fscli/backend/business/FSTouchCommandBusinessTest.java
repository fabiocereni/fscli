package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.IFSTouchCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

        touchCommandBusiness = FSTouchCommandBusiness.getInstance();

        fsState = FSStateBusiness.getInstance();


        root.addContent(home);
        home.addContent(user);

        fsState.setCurrentWorkingDirectory(user);
    }

    @Test
    public void touchTest() {
        String path = "/home/user";
        touchCommandBusiness.touch("test.txt", path);

        Optional<INode> tmp = PathSolver.resolvePath(path);
        assertTrue(tmp.isPresent());

        IDirectoryBusiness dir = (DirectoryBusiness) tmp.get();

        Optional<INode> elem = dir.getContent().stream()
                .filter(e -> e.getName().equals("test.txt"))
                .findFirst();

        assertTrue(elem.isPresent());
    }

}
