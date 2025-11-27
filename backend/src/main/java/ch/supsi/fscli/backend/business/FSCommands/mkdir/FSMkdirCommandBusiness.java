package ch.supsi.fscli.backend.business.FSCommands.mkdir;

import ch.supsi.fscli.backend.business.*;

public class FSMkdirCommandBusiness implements IFSMkdirCommandBusiness {

    private static FSMkdirCommandBusiness myself;
    private final FileSystem fileSystem = FileSystem.getInstance();


    private FSMkdirCommandBusiness() {}

    public static FSMkdirCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSMkdirCommandBusiness();
        return myself;
    }

    @Override
    public Boolean mkdir(String path) {

        if (path == null || path.isBlank())
            return false;

        if(path.charAt(0) == '/')
            path = path.substring(1);

        DirectoryInodeBusiness parentDir = PathSolver.extractParentDirectory(path);
        if (parentDir == null)
            return false; // parent non trovato

        String newDirName = PathSolver.extractFileName(path);

        // no: "" non è valido
        if (newDirName.isBlank())
            return false;

        // controllo duplicati
        if (PathSolver.nameAlreadyExists(parentDir, newDirName))
            return false;

        // crea directory (aggiunta automaticamente al parent)
        DirectoryInodeBusiness toAdd = fileSystem.createDirectory(parentDir);
        toAdd.addEntry(newDirName, parentDir);

        return true;
    }
}