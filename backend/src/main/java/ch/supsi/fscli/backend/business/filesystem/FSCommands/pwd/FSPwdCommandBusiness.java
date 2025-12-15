package ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd;

import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSPwdCommandBusiness implements IFSPwdCommandBusiness {

    private final FileSystem fileSystem;

    @Inject
    public FSPwdCommandBusiness(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public String pwd() {
        return fileSystem.getCurrentWorkingDirectoryPath();
    }
}
