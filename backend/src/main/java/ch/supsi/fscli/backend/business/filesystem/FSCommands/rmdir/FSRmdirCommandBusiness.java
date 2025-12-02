package ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class FSRmdirCommandBusiness implements IFSRmdirCommandBusiness {

    private final IFSStateBusiness state;
    private final PathSolver pathSolver;

    @Inject
    public FSRmdirCommandBusiness(IFSStateBusiness state, PathSolver pathSolver) {
        this.state = state;
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean rmdir(String path) {
        if (path == null || path.isBlank()) return false;

        if (path.endsWith("/") && !path.equals("/"))
            path = path.substring(0, path.length() - 1);

        Optional<Inode> nodeOpt = pathSolver.resolvePath(path);
        if (nodeOpt.isEmpty()) return false;

        Inode node = nodeOpt.get();
        if (node.getType() != InodeType.DIRECTORY) return false;

        DirectoryInodeBusiness targetDir = (DirectoryInodeBusiness) node;
        if (targetDir == state.getRoot()) return false;
        if (targetDir == state.getCurrentWorkingDirectory()) return false;
        if (!targetDir.getEntries().isEmpty()) return false;

        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory(path);
        if (parent == null) return false;

        String name = pathSolver.extractFileName(path);
        if (parent.getEntry(name) != targetDir) return false;

        parent.removeEntry(name);
        targetDir.decLinkCount();
        return true;
    }
}