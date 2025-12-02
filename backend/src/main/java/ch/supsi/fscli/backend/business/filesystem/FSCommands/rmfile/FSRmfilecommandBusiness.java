package ch.supsi.fscli.backend.business.filesystem.FSCommands.rmfile;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
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
