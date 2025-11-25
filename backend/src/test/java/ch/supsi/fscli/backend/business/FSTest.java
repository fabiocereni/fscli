package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class FSTest {

    private DirectoryInodeBusiness directory;
    private FileInodeBusiness file;

    private final FileSystem fileSystem = FileSystem.getInstance();
    private final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();


    @BeforeEach
    public void setup() {

        FSCreationBusiness.getInstance().newfs();

        // crea directory e file
        this.directory = fileSystem.createDirectory();
        this.file = fileSystem.createFile();

        // aggiungi alla CWD con un nome
        DirectoryInodeBusiness cwd = ifsStateBusiness.getCurrentWorkingDirectory();
        cwd.addEntry("TestDir", directory);
        cwd.addEntry("TestFile", file);
    }

    @Test
    public void testDirectoryMethods() {
        DirectoryInodeBusiness cwd = ifsStateBusiness.getCurrentWorkingDirectory();

        System.out.println("--- DIRECTORY TEST ---");

        // 1) ID corretto
        System.out.println("ID directory: " + directory.getId());

        // 2) Verificare che la CWD contiene la directory
        for (Map.Entry<String, Inode> entry : cwd.getEntries().entrySet()) {
            if (entry.getValue() == directory) {
                System.out.println("Directory name: " + entry.getKey());
            }
        }

        // 3) Tipo
        System.out.println("Directory type: " + directory.getClass().getSimpleName());
    }

    @Test
    public void testFileMethods() {
        DirectoryInodeBusiness cwd = ifsStateBusiness.getCurrentWorkingDirectory();

        System.out.println("--- FILE TEST ---");

        // 1) ID file
        System.out.println("ID file: " + file.getId());

        // 2) Nome del file (contenuto nella directory, non nell’inode)
        for (Map.Entry<String, Inode> entry : cwd.getEntries().entrySet()) {
            if (entry.getValue() == file) {
                System.out.println("File name: " + entry.getKey());
            }
        }

        // 3) Tipo
        System.out.println("File type: " + file.getClass().getSimpleName());
    }
}
