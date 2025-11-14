package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.FSRmfilecommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FSRmfilecommandBusinessTest {

    private FSRmfilecommandBusiness rmfileBusiness;
    private IFSStateBusiness stateBusiness;
    private DirectoryBusiness rootDir;
    private FileBusiness testFile;

    @BeforeEach
    void setUp() {
        rmfileBusiness = FSRmfilecommandBusiness.getInstance();
        stateBusiness = FSStateBusiness.getInstance();

        rootDir = new DirectoryBusiness(null, "root");
        testFile = new FileBusiness(rootDir, "file.txt");

        stateBusiness.setRoot(rootDir);
        stateBusiness.setCurrentWorkingDirectory(rootDir);
    }

    @Test
    void testRmfile() {
        System.out.println("Current working directory: ");
        stateBusiness.getCurrentWorkingDirectory().getContent().stream()
                .map(INode::getName)
                .forEach(System.out::println);

        boolean result = rmfileBusiness.rmfile("file.txt");
        assertTrue(result);

        System.out.println("Current working directory: ");
        stateBusiness.getCurrentWorkingDirectory().getContent().stream()
                .map(INode::getName)
                .forEach(System.out::println);
    }

}
