//package ch.supsi.fscli.backend.business;
//
//import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.FSTouchCommandBusiness;
//import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.IFSTouchCommandBusiness;
//
//import ch.supsi.fscli.backend.business.filesystem.state.FSStateBusiness;
//import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
//import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
//import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
//import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class FSTouchCommandBusinessTest {
//
//    private FSStateBusiness fsState;
//    private IFSTouchCommandBusiness touchCommandBusiness;
//    private PathSolver pathSolver;
//    private FileSystem fileSystem;
//
//    private DirectoryInodeBusiness root;
//    private DirectoryInodeBusiness home;
//    private DirectoryInodeBusiness user;
//
//    @BeforeEach
//    void setup() {
//        // 1. Setup manuale delle dipendenze (Root e State)
//        root = new DirectoryInodeBusiness(1L);
//        fsState = new FSStateBusiness();
//        fsState.setRoot(root);
//        fsState.setCurrentWorkingDirectory(root);
//        fsState.setCurrentWorkingDirectoryPath("/");
//
//        // 2. Setup FileSystem e PathSolver
//        fileSystem = new FileSystem(root);
//        pathSolver = new PathSolver(fsState);
//
//        // 3. Setup Command (Touch necessita di FileSystem per creare e PathSolver per risolvere i percorsi)
//        // Assumo che il costruttore di FSTouchCommandBusiness sia: (FileSystem, PathSolver)
//        touchCommandBusiness = new FSTouchCommandBusiness(fileSystem, pathSolver);
//
//        // 4. Creazione struttura directory
//        // createDirectory richiede il genitore come parametro (secondo il tuo FileSystem.java)
//
//        // crea /home
//        home = fileSystem.createDirectory(root);
//        root.addEntry("home", home);
//
//        // crea /home/user
//        user = fileSystem.createDirectory(home);
//        home.addEntry("user", user);
//
//        // CWD = /home/user
//        fsState.setCurrentWorkingDirectory(user);
//        fsState.setCurrentWorkingDirectoryPath("/home/user");
//    }
//
//    @Test
//    public void touchInSpecificPathTest() {
//        String path = "/home/user/test.txt";
//
//        boolean result = touchCommandBusiness.touch(path);
//        assertTrue(result);
//
//        // PathSolver non è statico, uso l'istanza pathSolver
//        Optional<Inode> resolved = pathSolver.resolvePath(path);
//        assertTrue(resolved.isPresent());
//
//        // verifico che il file sia realmente nella directory corretta
//        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory(path);
//        assertNotNull(parent);
//
//        Inode file = parent.getEntry("test.txt");
//        assertNotNull(file);
//    }
//
//    @Test
//    public void touchInCurrentDirectoryTest() {
//
//        boolean result = touchCommandBusiness.touch("file.txt");
//        assertTrue(result);
//
//        DirectoryInodeBusiness cwd = fsState.getCurrentWorkingDirectory();
//        Inode file = cwd.getEntry("file.txt");
//
//        assertNotNull(file);
//    }
//
//    @Test
//    public void touchInvalidPathTest() {
//        // Nota: Assicurati che il tuo FSTouchCommandBusiness lanci effettivamente
//        // una eccezione e non ritorni solo 'false' in caso di percorso errato.
//        assertThrows(IllegalArgumentException.class,
//                () -> touchCommandBusiness.touch("/invalid/path/fail.txt"));
//    }
//}