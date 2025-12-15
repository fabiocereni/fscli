package ch.supsi.fscli.backend.business.filesystem.creation;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class FSCreationBusiness implements IFSCreationBusiness {

    private final FileSystem fileSystem;

    @Inject
    public FSCreationBusiness(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public void newfs() {
        fileSystem.reset();
    }
}
