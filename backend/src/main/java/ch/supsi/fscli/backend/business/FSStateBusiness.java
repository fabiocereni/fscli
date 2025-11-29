package ch.supsi.fscli.backend.business;

import com.google.inject.Singleton;

@Singleton
public class FSStateBusiness implements IFSStateBusiness {

    private boolean saved = false;
    private DirectoryInodeBusiness root;
    private DirectoryInodeBusiness currentWorkingDirectory;
    private String currentWorkingDirectoryPath = "/";

    public FSStateBusiness() {}

    @Override
    public boolean changeSavedStateAndGet() {
        saved = !saved;
        return saved;
    }

    @Override
    public DirectoryInodeBusiness getRoot() {
        return this.root;
    }

    @Override
    public DirectoryInodeBusiness getCurrentWorkingDirectory() {
        return this.currentWorkingDirectory;
    }

    @Override
    public void setCurrentWorkingDirectory(DirectoryInodeBusiness directory) {
        this.currentWorkingDirectory = directory;
    }

    @Override
    public void setRoot(DirectoryInodeBusiness root) {
        this.root = root;
    }

    @Override
    public String getCurrentWorkingDirectoryPath() {
        return currentWorkingDirectoryPath;
    }

    @Override
    public void setCurrentWorkingDirectoryPath(String currentWorkingDirectoryPath) {
        this.currentWorkingDirectoryPath = currentWorkingDirectoryPath;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
