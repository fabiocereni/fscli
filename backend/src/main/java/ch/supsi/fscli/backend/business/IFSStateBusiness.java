package ch.supsi.fscli.backend.business;

public interface IFSStateBusiness {
    boolean changeSavedStateAndGet();
    void setRoot(DirectoryInodeBusiness root);
    DirectoryInodeBusiness getRoot();
    DirectoryInodeBusiness getCurrentWorkingDirectory();
    void setCurrentWorkingDirectory(DirectoryInodeBusiness directory);
    String getCurrentWorkingDirectoryPath();
    void setCurrentWorkingDirectoryPath(String currentWorkingDirectoryPath);
}
