package ch.supsi.fscli.backend.business.FSCommands.rmdir;

import ch.supsi.fscli.backend.business.*;

import java.util.Iterator;

public class FSRmdirCommandBusiness implements IFSRmdirCommandBusiness {

    private static FSRmdirCommandBusiness myself;
    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

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

        if(path.charAt(0) == '/')
            path = path.substring(1);

        // Risolvo il nodo target usando PathSolver
        INode targetNode = PathSolver.resolvePath(path).orElse(null);
        if (targetNode == null || targetNode.getType() != NodeType.DIRECTORY)
            return false; // non esiste o non è una directory

        DirectoryBusiness targetDir = (DirectoryBusiness) targetNode;

        if (!targetDir.getContent().isEmpty())
            return false; // directory non vuota

        IDirectoryBusiness parentDir = targetDir.getParent();
        if (parentDir == null)
            return false; // non si può rimuovere la root

        return parentDir.getContent().remove(targetDir);
    }
}
