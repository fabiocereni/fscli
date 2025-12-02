package ch.supsi.fscli.backend.business.persistence;


import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.state.FSStateBusiness;

public class PersistedWrapper {

    private FSStateBusiness stateBusiness;
    private FileSystem fileSystem;

    public PersistedWrapper() {}

    public FSStateBusiness getStateBusiness() {
        return stateBusiness;
    }

    public void setStateBusiness(FSStateBusiness stateBusiness) {
        this.stateBusiness = stateBusiness;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
}
