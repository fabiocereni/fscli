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

        // NON rimuovere lo slash!
        // PathSolver ora gestisce correttamente gli absolute path

        DirectoryInodeBusiness parentDir = PathSolver.extractParentDirectory(path);
        if (parentDir == null)
            return false; // parent non trovato

        String newDirName = PathSolver.extractFileName(path);

        // "" non valido
        if (newDirName.isBlank())
            return false;

        // controllo duplicati
        if (PathSolver.nameAlreadyExists(parentDir, newDirName))
            return false;

        // crea la directory
        DirectoryInodeBusiness newDir = fileSystem.createDirectory(parentDir);

        // registra nel parent la nuova entry
        parentDir.addEntry(newDirName, newDir);

        return true;
    }
}