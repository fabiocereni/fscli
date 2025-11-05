package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FSTest {

    private IDirectoryBusiness directory;
    private IFileBusiness file;

    @BeforeEach
    public void setup() {
        this.directory = new DirectoryBusiness(null, "Test directory name");
        this.file = new FileBusinessBusiness(FileBusinessBusiness.getParentDirectory(directory), "Test file name");
    }

    @Test
    public void testDirectoryMethods() {
        this.directory.mkdir();
        this.directory.pwd();

        System.out.println("Directory name: " + this.directory.getName());
        System.out.println("Directory parent name: " + this.directory.getParent());
        System.out.println("Directory is a: " + this.directory.getClass());

    }

    @Test
    public void testFileMethods() {
        this.file.touch("Test.txt");
        this.file.pwd();

        System.out.println("File name: " + this.file.getName());
        System.out.println("File parent name: " + this.file.getParent().getName());
        System.out.println("File is a: " + this.file.getClass());

    }
}