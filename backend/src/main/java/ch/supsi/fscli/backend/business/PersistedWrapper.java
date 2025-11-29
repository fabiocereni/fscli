package ch.supsi.fscli.backend.business;


public class PersistedWrapper {

    private FSStateBusiness stateBusiness;
    private FileSystem fileSystem;

    // necessario per Jackson ma SENZA logica interna
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
