package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.ln.FSLnCommandBusiness; // Import se necessario, o altri comandi
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PathSolverTest {

    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness home;
    private DirectoryInodeBusiness user;
    private FileInodeBusiness photo;

    // Riferimento al Singleton (per resettarlo)
    private FSStateBusiness state;

    @BeforeEach
    void setUp() {
        state = FSStateBusiness.getInstance();
        FileSystem fs = FileSystem.getInstance(); // Assumendo che FileSystem sia necessario per creare nodi

        // 1. Reset completo dello stato (simulazione pulita)
        root = new DirectoryInodeBusiness(100); // 100 = esempio dimensione
        home = new DirectoryInodeBusiness(100);
        user = new DirectoryInodeBusiness(100);
        DirectoryInodeBusiness docs = new DirectoryInodeBusiness(100);
        DirectoryInodeBusiness bin = new DirectoryInodeBusiness(100);

        // Creazione file tramite factory o costruttore
        photo = fs.createFile();

        // 2. Costruzione Albero
        // /home
        root.addEntry("home", home);
        root.addEntry("bin", bin);

        // /home/user
        home.addEntry("user", user);

        // /home/user/docs
        user.addEntry("docs", docs);
        // /home/user/photo.jpg
        user.addEntry("photo.jpg", photo);

        // 3. Impostazione Stato Iniziale
        state.setRoot(root);
        state.setCurrentWorkingDirectory(root); // Partiamo dalla root
    }

    // --- TEST RESOLVE PATH ---

    @Test
    void testResolveRoot() {
        Optional<Inode> result = PathSolver.resolvePath("/");
        assertTrue(result.isPresent());
        assertEquals(root, result.get());
    }

    @Test
    void testResolveAbsolutePathSuccess() {
        // Test percorso: /home/user/photo.jpg
        Optional<Inode> result = PathSolver.resolvePath("/home/user/photo.jpg");

        assertTrue(result.isPresent());
        assertEquals(photo, result.get());
    }

    @Test
    void testResolveRelativePathFromRoot() {
        // CWD è Root. Cerco: home/user
        Optional<Inode> result = PathSolver.resolvePath("home/user");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testResolveRelativePathFromSubDir() {
        // Cambio CWD a /home
        state.setCurrentWorkingDirectory(home);

        // Cerco: user/photo.jpg
        Optional<Inode> result = PathSolver.resolvePath("user/photo.jpg");

        assertTrue(result.isPresent());
        assertEquals(photo, result.get());
    }

    @Test
    void testResolvePathWithDots() {
        // Test percorso: /home/./user (il punto non deve cambiare nulla)
        Optional<Inode> result = PathSolver.resolvePath("/home/./user");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testResolvePathWithDoubleDotsInString() {
        // Test percorso: /home/user/../user/photo.jpg
        // user/.. annulla user, quindi torna a home, poi rientra in user
        Optional<Inode> result = PathSolver.resolvePath("/home/user/../user/photo.jpg");

        assertTrue(result.isPresent());
        assertEquals(photo, result.get());
    }

    @Test
    void testResolvePathNotFound() {
        Optional<Inode> result = PathSolver.resolvePath("/home/user/nonEsiste.txt");
        assertTrue(result.isEmpty());
    }

    @Test
    void testResolvePathInvalidDoubleSlash() {
        // Il tuo codice controlla "if (path.contains("//"))"
        Optional<Inode> result = PathSolver.resolvePath("/home//user");
        assertTrue(result.isEmpty());
    }

    @Test
    void testResolveNullOrEmpty() {
        assertTrue(PathSolver.resolvePath(null).isEmpty());
        assertTrue(PathSolver.resolvePath("").isEmpty());
    }

    // --- TEST EXTRACT PARENT DIRECTORY ---

    @Test
    void testExtractParentFromFile() {
        // path: /home/user/photo.jpg -> parent deve essere /home/user
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/home/user/photo.jpg");

        assertNotNull(parent);
        assertEquals(user, parent);
    }

    @Test
    void testExtractParentFromDirectoryWithTrailingSlash() {
        // path: /home/user/ -> parent deve essere /home
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/home/user/");

        assertNotNull(parent);
        assertEquals(home, parent);
    }

    @Test
    void testExtractParentRoot() {
        // Root non ha parent (o meglio, la logica attuale potrebbe ritornare null o root)
        // Guardando il tuo codice: lastIndexOf("/") == 0 -> return root
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/fileInRoot");
        assertEquals(root, parent);
    }

    @Test
    void testExtractParentSimpleName() {
        // path: "file.txt" (relativo) -> parent è CWD
        state.setCurrentWorkingDirectory(user);

        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("photo.jpg");
        assertEquals(user, parent);
    }

    @Test
    void testExtractParentNotExists() {
        // Il path padre "/home/ghost" non esiste
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory("/home/ghost/file.txt");
        assertNull(parent);
    }

    // --- TEST EXTRACT FILE NAME ---

    @Test
    void testExtractFileNameAbsolute() {
        String name = PathSolver.extractFileName("/home/user/photo.jpg");
        assertEquals("photo.jpg", name);
    }

    @Test
    void testExtractFileNameSimple() {
        String name = PathSolver.extractFileName("readme.txt");
        assertEquals("readme.txt", name);
    }

    // --- TEST NAME ALREADY EXISTS ---

    @Test
    void testNameAlreadyExists() {
        assertTrue(PathSolver.nameAlreadyExists(user, "photo.jpg"));
        assertFalse(PathSolver.nameAlreadyExists(user, "video.mp4"));
    }
}