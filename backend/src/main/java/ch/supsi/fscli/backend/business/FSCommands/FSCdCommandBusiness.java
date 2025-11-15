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

        /*
        if (name == null || name.isBlank())
            return false;

        DirectoryBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
        DirectoryBusiness rootDir = stateBusiness.getRoot();

        if (currentDir == null || rootDir == null)
            return false;

        if (name.equals("/")) {
            stateBusiness.setCurrentWorkingDirectory(rootDir);
            return true;
        }

        if (name.equals("..")) {
            IDirectoryBusiness parent = currentDir.getParent();
            if (parent != null)
                stateBusiness.setCurrentWorkingDirectory((DirectoryBusiness) parent);
            return true;
        }

        Optional<INode> targetNode = currentDir.getContent().stream()
                .filter(node -> node.getName().equals(name))
                .findFirst();

        if (targetNode.isEmpty()) {
            System.out.println("cd: directory not found '" + name + "'");
            return false;
        }

        INode target = targetNode.get();
        if (target.getType() != NodeType.DIRECTORY) {
            System.out.println("cd: not a directory: '" + name + "'");
            return false;
        }

        stateBusiness.setCurrentWorkingDirectory((DirectoryBusiness) target);
        return true;
         */
    }
}
