package ch.supsi.fscli.backend.business.FSCommands.mv;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;

public class FSMvCommandBusiness implements IFSMvCommandBusiness {

    private static FSMvCommandBusiness myself;

    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSMvCommandBusiness() {}

    public static FSMvCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSMvCommandBusiness();
        return myself;
    }

    @Override
    public boolean mv(String source, String destination) {
        if (source == null || destination == null || source.isBlank() || destination.isBlank())
            return false;

        DirectoryBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
        if (currentDir == null)
            return false;

        Optional<INode> sourceNodeOpt = PathSolver.resolvePath(source);
        if (sourceNodeOpt.isEmpty())
            return false;

        INode sourceNode = sourceNodeOpt.get();

        Optional<INode> destinationNodeOpt = PathSolver.resolvePath(destination);

        if (destinationNodeOpt.isPresent()) {
            INode destinationNode = destinationNodeOpt.get();

            // la destinazione non puo' essere un file
            if (destinationNode.getType() == NodeType.FILE)
                return false;

            DirectoryBusiness destinationDir = (DirectoryBusiness) destinationNode;

            if (sourceNode == destinationDir)
                return false;

            boolean isPresentName = destinationDir.getContent().stream()
                    .anyMatch(node -> node.getName().equals(sourceNode.getName()));
            if (isPresentName)
                return false;

            currentDir.getContent().remove(sourceNode);
            destinationDir.getContent().add(sourceNode);
            sourceNode.setParent(destinationDir);
            return true;
        } else {
            sourceNode.setName(destination);
            return true;
        }
    }

}
