package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.*;
import ch.supsi.fscli.backend.business.FSCommands.mkdir.FSMkdirCommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FSMkdirCommandBusinessTest {

    private FileSystem fileSystem;
    private IFSStateBusiness ifsState;
    private DirectoryInodeBusiness root;
    private FSMkdirCommandBusiness mkdirBusiness;

    @BeforeEach
    void setup() {
        // ricrea tutto il filesystem da zero
        FSCreationBusiness.getInstance().newfs();

        fileSystem = FileSystem.getInstance();
        ifsState = FSStateBusiness.getInstance();
        mkdirBusiness = FSMkdirCommandBusiness.getInstance();

        root = ifsState.getRoot();
        assertNotNull(root);

        // set directory corrente
        ifsState.setCurrentWorkingDirectory(root);
    }

    // ----------------------------------------------------------
    // 1) Path null o vuoto
    // ----------------------------------------------------------
    @Test
    void testNullPath() {
        assertFalse(mkdirBusiness.mkdir(null));
    }

    @Test
    void testBlankPath() {
        assertFalse(mkdirBusiness.mkdir(""));
        assertFalse(mkdirBusiness.mkdir("   "));
    }

    // ----------------------------------------------------------
    // 2) Creazione base
    // ----------------------------------------------------------
    @Test
    void testCreateDirectoryInRoot() {
        boolean ok = mkdirBusiness.mkdir("docs");
        assertTrue(ok);
        DirectoryInodeBusiness created = (DirectoryInodeBusiness) root.getEntry("docs");
        assertNotNull(created);
        System.out.println(created.getEntries());
        assertTrue(created instanceof DirectoryInodeBusiness);
    }


}
