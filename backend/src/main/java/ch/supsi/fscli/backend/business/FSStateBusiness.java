package ch.supsi.fscli.backend.business;

public class FSStateBusiness implements IFSStateBusiness {

    private boolean saved = false;
    private DirectoryBusiness root;
    private DirectoryBusiness currentWorkingDirectory;

    private static FSStateBusiness myself;

    private FSStateBusiness() {}

    public static FSStateBusiness getInstance() {
        if (myself == null)
            myself = new FSStateBusiness();

        return myself;
    }

    @Override
    public boolean changeSavedStateAndGet() {
        saved = !saved;
        return saved;
    }

    @Override
    public DirectoryBusiness getRoot() {
        return this.root;
    }

    @Override
    public DirectoryBusiness getCurrentWorkingDirectory() {
        return this.currentWorkingDirectory;
    }

    @Override
    public void setCurrentWorkingDirectory(DirectoryBusiness directory) {
        this.currentWorkingDirectory = directory;
    }

    @Override
    public void setRoot(DirectoryBusiness root) {
        this.root = root;
    }
}