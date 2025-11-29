package ch.supsi.fscli.backend.business.FSCommands.cd;

import ch.supsi.fscli.backend.business.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.IFSStateBusiness;
import ch.supsi.fscli.backend.business.Inode;
import ch.supsi.fscli.backend.business.InodeType;
import ch.supsi.fscli.backend.business.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class FSCdCommandBusiness implements IFSCdCommandBusiness {

    private final IFSStateBusiness stateBusiness;
    private final PathSolver pathSolver;

    @Inject
    public FSCdCommandBusiness(IFSStateBusiness stateBusiness, PathSolver pathSolver) {
        this.stateBusiness = stateBusiness;
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean cd(String name) {

        Optional<Inode> targetNodeOpt = pathSolver.resolvePath(name);
        if (targetNodeOpt.isEmpty())
            return false;

        Inode targetNode = targetNodeOpt.get();
        if (targetNode.getType() != InodeType.DIRECTORY)
            return false;

        stateBusiness.setCurrentWorkingDirectory((DirectoryInodeBusiness) targetNode);
        stateBusiness.setCurrentWorkingDirectoryPath(name);

        return true;
    }
}
