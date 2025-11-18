package ch.supsi.fscli.backend.business.FSCommands;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;

public class FSCdCommandBusiness implements IFSCdCommandBusiness {

    private static FSCdCommandBusiness myself;

    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSCdCommandBusiness() {}

    public static FSCdCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSCdCommandBusiness();
        return myself;
    }


    @Override
    public boolean cd(String name) {

        Optional<INode> targetNodeOpt = PathSolver.resolvePath(name);

        if (targetNodeOpt.isEmpty()) {
            return false;
        }

        INode targetNode = targetNodeOpt.get();
        if (targetNode.getType() != NodeType.DIRECTORY) {
            return false;
        }
        stateBusiness.setCurrentWorkingDirectory((DirectoryBusiness) targetNode);
        return true;
    }
}
