package ch.supsi.fscli.backend.business.FSCommands.rmfile;

import ch.supsi.fscli.backend.business.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.FileSystem;
import ch.supsi.fscli.backend.business.Inode;
import ch.supsi.fscli.backend.business.InodeType;
import ch.supsi.fscli.backend.business.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSRmfilecommandBusiness implements IFSRmfileCommandBusiness {

    private final FileSystem fileSystem;
    private final PathSolver pathSolver;

    @Inject
    public FSRmfilecommandBusiness(FileSystem fileSystem, PathSolver pathSolver) {
        this.fileSystem = fileSystem;
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean rmfile(String name) {

        if (name == null || name.isBlank())
            return false;

        DirectoryInodeBusiness parentDir = pathSolver.extractParentDirectory(name);
        if (parentDir == null)
            return false;

        String fileName = pathSolver.extractFileName(name);
        if (fileName.isBlank())
            return false;

        Inode target = parentDir.getEntry(fileName);
        if (target == null)
            return false;

        if (target.getType() == InodeType.DIRECTORY)
            return false;

        parentDir.removeEntry(fileName);
        target.decLinkCount();

        return true;
    }
}
