package ch.supsi.fscli.backend.business.FSCommands.touch;

import ch.supsi.fscli.backend.business.*;

public class FSTouchCommandBusiness implements IFSTouchCommandBusiness {

    private static FSTouchCommandBusiness myself;

    private final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    private FSTouchCommandBusiness() {}

    public static FSTouchCommandBusiness getInstance() {
        if(myself == null)
            myself = new FSTouchCommandBusiness();

        return myself;
    }


    @Override
    public boolean touch(String path) throws IllegalArgumentException {

        if(path == null)
            throw new IllegalArgumentException("touch: path not valid");


        IDirectoryBusiness parentDirectory = PathSolver.extractParentDirectory(path);

        if (parentDirectory == null)
            throw new IllegalArgumentException("touch: path not valid");

        String newFileName = PathSolver.extractFileName(path);

        if (PathSolver.nameAlreadyExists(parentDirectory, newFileName))
            throw new IllegalArgumentException("touch: file with same name already exists");

        new FileBusiness(parentDirectory, newFileName);

        return true;
    }
}
