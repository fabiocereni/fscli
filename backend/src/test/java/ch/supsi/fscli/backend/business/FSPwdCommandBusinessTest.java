//package ch.supsi.fscli.backend.business;
//
//import ch.supsi.fscli.backend.business.FSCommands.pwd.FSPwdCommandBusiness;
//import ch.supsi.fscli.backend.business.FSCommands.pwd.IFSPwdCommandBusiness;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class FSPwdCommandBusinessTest {
//
//    private IFSStateBusiness fsState;
//    private IFSPwdCommandBusiness pwdCommandBusiness;
//
//    private DirectoryInodeBusiness root;
//    private DirectoryInodeBusiness home;
//    private DirectoryInodeBusiness user;
//
//    @BeforeEach
//    void setup() {
//
//        FSCreationBusiness.getInstance().newfs();
//        fsState = FSStateBusiness.getInstance();
//
//        // crea root
//        root = FileSystem.getInstance().createDirectory();
//        fsState.setRoot(root);
//        fsState.setCurrentWorkingDirectoryPath("/");
//        fsState.setCurrentWorkingDirectory(root);
//
//        // crea /home
//        home = FileSystem.getInstance().createDirectory();
//        root.addEntry("home", home);
//
//        // crea /home/user
//        user = FileSystem.getInstance().createDirectory();
//        home.addEntry("user", user);
//
//        pwdCommandBusiness = FSPwdCommandBusiness.getInstance();
//    }
//
//    @Test
//    public void pwdTest() {
//
//        // CWD = /Test
//        DirectoryInodeBusiness testDir = FileSystem.getInstance().createDirectory();
//        root.addEntry("Test", testDir);
//
//        fsState.setCurrentWorkingDirectory(testDir);
//        fsState.setCurrentWorkingDirectoryPath("/Test");
//
//        assertEquals("/Test", pwdCommandBusiness.pwd());
//
//        // CWD = /home/user
//        fsState.setCurrentWorkingDirectory(user);
//        fsState.setCurrentWorkingDirectoryPath("/home/user");
//
//        assertEquals("/home/user", pwdCommandBusiness.pwd());
//
//        // CWD = /home
//        fsState.setCurrentWorkingDirectory(home);
//        fsState.setCurrentWorkingDirectoryPath("/home");
//
//        assertEquals("/home", pwdCommandBusiness.pwd());
//    }
//}
