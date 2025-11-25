package ch.supsi.fscli.backend.business.FSCommands.mkdir;

import ch.supsi.fscli.backend.business.*;

public class FSMkdirCommandBusiness implements IFSMkdirCommandBusiness {

    private static FSMkdirCommandBusiness myself;

    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSMkdirCommandBusiness() {}

    public static FSMkdirCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSMkdirCommandBusiness();
        return myself;
    }

    @Override
    public Boolean mkdir(String path) {

        // 1. Invalid input
        if (path == null || path.isBlank())
            return false;

        // 2. Parent directory tramite PathSolver
        IDirectoryBusiness parentDir = PathSolver.extractParentDirectory(path);
        if (parentDir == null)
            return false;  // parent non valido o non directory

        // 3. Estraggo il nome finale
        String name = PathSolver.extractFileName(path);

        if (name.isBlank())
            return false;

        // 4. Controllo se esiste già
        if (PathSolver.nameAlreadyExists(parentDir, name))
            return false;

        // 5. Creo la directory
        new DirectoryBusiness(parentDir, name);

        return true;
    }
}