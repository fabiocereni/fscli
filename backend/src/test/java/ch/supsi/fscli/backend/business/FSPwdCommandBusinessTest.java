package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.FSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.IFSPwdCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FSPwdCommandBusinessTest {

    private IFSStateBusiness fsState;
    private IFSPwdCommandBusiness pwdCommandBusiness;
    private DirectoryBusiness home;
    private DirectoryBusiness user;

    @BeforeEach
    void setup() {
        FSStateBusiness.getInstance().setRoot(new DirectoryBusiness(null, "/"));
        DirectoryBusiness root = FSStateBusiness.getInstance().getRoot();
        root.getContent().clear();
        this.home = new DirectoryBusiness(root, "home");
        this.user = new DirectoryBusiness(home, "user");

        pwdCommandBusiness = FSPwdCommandBusiness.getInstance();

        fsState = FSStateBusiness.getInstance();


        root.addContent(home);
        home.addContent(user);

        fsState.setCurrentWorkingDirectory(user);
    }

    @Test
    public void pwdTest() {
        fsState.setCurrentWorkingDirectory(new DirectoryBusiness(fsState.getRoot(), "Test"));
        assertEquals(pwdCommandBusiness.pwd(), "/Test");
        System.out.println(pwdCommandBusiness.pwd());

        fsState.setCurrentWorkingDirectory(user);
        assertEquals(pwdCommandBusiness.pwd(), "/home/user");
        System.out.println(pwdCommandBusiness.pwd());

        fsState.setCurrentWorkingDirectory(home);
        assertEquals(pwdCommandBusiness.pwd(), "/home");
        System.out.println(pwdCommandBusiness.pwd());
    }
}