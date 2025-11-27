package ch.supsi.fscli.backend.business.FSCommands.rmdir;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;

public class FSRmdirCommandBusiness implements IFSRmdirCommandBusiness {

    private static FSRmdirCommandBusiness myself;

    private final IFSStateBusiness state = FSStateBusiness.getInstance();

    private FSRmdirCommandBusiness() {}

    public static FSRmdirCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSRmdirCommandBusiness();

        return myself;
    }

    @Override
    public boolean rmdir(String path) {

        if (path == null || path.isBlank())
            return false;

        // Normalizza path: rimuovi "/" finale (se non è root)
        if (path.endsWith("/") && !path.equals("/"))
            path = path.substring(0, path.length() - 1);

        // 1) Trova directory target con PathSolver
        Optional<Inode> nodeOpt = PathSolver.resolvePath(path);
        if (nodeOpt.isEmpty())
            return false;

        Inode node = nodeOpt.get();

        // 2) Deve essere una DIRECTORY
        if (node.getType() != InodeType.DIRECTORY)
            return false;

        DirectoryInodeBusiness targetDir = (DirectoryInodeBusiness) node;

        // 3) Non puoi rimuovere la root
        if (targetDir == state.getRoot())
            return false;

        // 4) Deve essere vuota
        if (!targetDir.getEntries().isEmpty())
            return false;

        // 5) Ricava il parent via PathSolver
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory(path);
        if (parent == null)
            return false;

        // 6) Ricava il nome della directory da eliminare
        String name = PathSolver.extractFileName(path);
        if (name == null || name.isBlank())
            return false;

        // 7) Rimuovi entry dal parent
        if (parent.getEntry(name) != targetDir)
            return false; // incoerenza → non rimuovere

        parent.removeEntry(name);

        // 8) Aggiorna link count
        targetDir.decLinkCount();
        // (opzionale) se linkCount == 0 → potresti deallocare l’inode dal FileSystem

        return true;
    }
}