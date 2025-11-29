package ch.supsi.fscli.backend.business;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSStateBusiness.class)
public interface IFSStateBusiness {
    boolean changeSavedStateAndGet();
    void setRoot(DirectoryInodeBusiness root);
    DirectoryInodeBusiness getRoot();
    DirectoryInodeBusiness getCurrentWorkingDirectory();
    void setCurrentWorkingDirectory(DirectoryInodeBusiness directory);
    String getCurrentWorkingDirectoryPath();
    void setCurrentWorkingDirectoryPath(String currentWorkingDirectoryPath);
}
