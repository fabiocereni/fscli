package ch.supsi.fscli.backend.business.FSCommands.mkdir;

import ch.supsi.fscli.backend.business.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.FileSystem;
import ch.supsi.fscli.backend.business.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSMkdirCommandBusiness implements IFSMkdirCommandBusiness {

    private final FileSystem fileSystem;
    private final PathSolver pathSolver;

    @Inject
    public FSMkdirCommandBusiness(FileSystem fileSystem, PathSolver pathSolver) {
        this.fileSystem = fileSystem;
        this.pathSolver = pathSolver;
    }

    @Override
    public Boolean mkdir(String path) {

        if (path == null || path.isBlank())
            return false;

        DirectoryInodeBusiness parentDir = pathSolver.extractParentDirectory(path);
        if (parentDir == null)
            return false;

        String newDirName = pathSolver.extractFileName(path);

        if (newDirName.isBlank())
            return false;

        if (pathSolver.nameAlreadyExists(parentDir, newDirName))
            return false;

        DirectoryInodeBusiness newDir = fileSystem.createDirectory(parentDir);
        parentDir.addEntry(newDirName, newDir);

        return true;
    }
}
