package ch.supsi.fscli.backend.business.filesystem.FSCommands.touch;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSTouchCommandBusiness implements IFSTouchCommandBusiness {

    private final FileSystem fileSystem;
    private final PathSolver pathSolver;

    @Inject
    public FSTouchCommandBusiness(FileSystem fileSystem, PathSolver pathSolver) {
        this.fileSystem = fileSystem;
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean touch(String path) throws IllegalArgumentException {

        if (path == null)
            throw new IllegalArgumentException("touch: path not valid");

        DirectoryInodeBusiness parentDirectory = pathSolver.extractParentDirectory(path);

        if (parentDirectory == null)
            throw new IllegalArgumentException("touch: path not valid");

        String newFileName = pathSolver.extractFileName(path);

        if (pathSolver.nameAlreadyExists(parentDirectory, newFileName))
            throw new IllegalArgumentException("touch: file with same name already exists");

        FileInodeBusiness toCreate = fileSystem.createFile();
        parentDirectory.addEntry(newFileName, toCreate);

        return true;
    }
}
