package ch.supsi.fscli.backend.business;

public interface IFSStateBusiness {
    boolean changeSavedStateAndGet();
    void setRoot(DirectoryBusiness root);
    DirectoryBusiness getRoot();
    DirectoryBusiness getCurrentWorkingDirectory();
    void setCurrentWorkingDirectory(DirectoryBusiness directory);
}
