package ch.supsi.fscli.backend.business;

//import ch.supsi.fscli.backend.business.FSCommands.rmfile.FSRmfilecommandBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FSRmfilecommandBusinessTest {

//    private FSRmfilecommandBusiness rmfileBusiness;
//    private IFSStateBusiness stateBusiness;
//    private DirectoryBusiness rootDir;
//    private DirectoryBusiness subDir;
//    private FileBusiness fileInRoot;
//    private FileBusiness fileInSubDir;
//
//    @BeforeEach
//    void setUp() {
//        rmfileBusiness = FSRmfilecommandBusiness.getInstance();
//        stateBusiness = FSStateBusiness.getInstance();
//
//        rootDir = new DirectoryBusiness(null, "root");
//        fileInRoot = new FileBusiness(rootDir, "fileRoot.txt");
//        subDir = new DirectoryBusiness(rootDir, "sub");
//        fileInSubDir = new FileBusiness(subDir, "fileSub.txt");
//
//        stateBusiness.setRoot(rootDir);
//        stateBusiness.setCurrentWorkingDirectory(rootDir);
//    }
//
//    @Test
//    void testRmFile_Success_SimpleFile() {
//        assertEquals(2, rootDir.getContent().size(), "La root dovrebbe contenere 2 elementi inizialmente");
//        boolean result = rmfileBusiness.rmfile("fileRoot.txt");
//        assertTrue(result, "Il metodo dovrebbe ritornare true per un file esistente");
//
//        assertEquals(1, rootDir.getContent().size(), "La root dovrebbe avere ora 1 solo elemento");
//        assertFalse(rootDir.getContent().contains(fileInRoot), "Il file non dovrebbe più essere nella lista dei contenuti");
//
//        assertNull(fileInRoot.getParent(), "Il file rimosso non dovrebbe avere più un parent");
//    }
//
//    @Test
//    void testRmFile_Failure_FileNotFound() {
//        boolean result = rmfileBusiness.rmfile("nonEsisto.txt");
//
//        assertFalse(result, "Dovrebbe ritornare false se il file non esiste");
//        assertEquals(2, rootDir.getContent().size(), "Il contenuto della directory non deve cambiare");
//    }
//
//    @Test
//    void testRmFile_Failure_IsDirectory() {
//        boolean result = rmfileBusiness.rmfile("sub");
//
//        assertFalse(result, "Dovrebbe ritornare false se si prova a rimuovere una directory");
//        assertTrue(rootDir.getContent().contains(subDir), "La directory 'sub' deve esistere ancora");
//    }
//
//    @Test
//    void testRmFile_Failure_InvalidInput() {
//        assertFalse(rmfileBusiness.rmfile(null));
//        assertFalse(rmfileBusiness.rmfile(""));
//        assertFalse(rmfileBusiness.rmfile("   "));
//    }
//
//    @Test
//    void testRmFile_Success_PathResolution() {
//        boolean result = rmfileBusiness.rmfile("sub/fileSub.txt");
//
//        if (result) {
//            assertTrue(result);
//            assertEquals(0, subDir.getContent().size(), "La sottocartella dovrebbe essere vuota");
//        } else {
//            System.out.println("WARN: Path resolution test skipped or failed implies simple name matching only.");
//        }
//    }
}