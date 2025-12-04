package ch.supsi.fscli.backend.business.persistence;


import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;

public class PersistedWrapper {

    private FileSystem fileSystem;

    public PersistedWrapper() {}

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
}
