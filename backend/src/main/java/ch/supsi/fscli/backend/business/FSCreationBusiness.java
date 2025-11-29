package ch.supsi.fscli.backend.business;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationBusiness implements IFSCreationBusiness {

    private final FileSystem fileSystem;
    private final IFSStateBusiness ifsStateBusiness;

    @Inject
    public FSCreationBusiness(FileSystem fileSystem,
                              IFSStateBusiness ifsStateBusiness) {
        this.fileSystem = fileSystem;
        this.ifsStateBusiness = ifsStateBusiness;
    }

    @Override
    public void newfs() {
        DirectoryInodeBusiness root = fileSystem.getRoot();
        ifsStateBusiness.setRoot(root);
        ifsStateBusiness.setCurrentWorkingDirectory(root);
        System.out.println("NEW FILESYSTEM CREATED");
    }
}
