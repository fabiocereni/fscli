package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.FSMkdirCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSMkdirCommandBusinessTest {

    private FSMkdirCommandBusiness mkdirBusiness;
    private IFSStateBusiness stateBusiness;
    private DirectoryBusiness rootDir;

    @BeforeEach
    void setUp() {
        mkdirBusiness = FSMkdirCommandBusiness.getInstance();
        stateBusiness = FSStateBusiness.getInstance();

        // reset stato file system per il test
        rootDir = new DirectoryBusiness(null, "root");
        stateBusiness.setRoot(rootDir);
        stateBusiness.setCurrentWorkingDirectory(rootDir);
    }

    @Test
    void testMkdirSuccess() {
        boolean result = mkdirBusiness.mkdir("newDir");
        assertTrue(result, "Directory should be created");

        // verifica che la directory sia effettivamente nella lista
        boolean exists = rootDir.getContent()
                .stream()
                .anyMatch(d -> d.getName().equals("newDir"));
        assertTrue(exists, "Directory must exist in content list");
    }

    @Test
    void testMkdirAlreadyExists() {
        mkdirBusiness.mkdir("existing");
        boolean result = mkdirBusiness.mkdir("existing");
        assertFalse(result, "Should not create directory with existing name");
    }

    @Test
    void testMkdirInvalidName() {
        assertFalse(mkdirBusiness.mkdir(null), "Null name should fail");
        assertFalse(mkdirBusiness.mkdir(""), "Empty name should fail");
        assertFalse(mkdirBusiness.mkdir("   "), "Blank name should fail");
    }
}