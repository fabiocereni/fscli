package ch.supsi.fscli.backend.business.filesystem.creation;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationBusiness implements IFSCreationBusiness {

    private final FileSystem fileSystem;

    @Inject
    public FSCreationBusiness(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public void newfs() {
        DirectoryInodeBusiness root = fileSystem.getRoot();
        fileSystem.setRoot(root);
        fileSystem.setCurrentWorkingDirectory(root);
        System.out.println("NEW FILESYSTEM CREATED");
    }
}
