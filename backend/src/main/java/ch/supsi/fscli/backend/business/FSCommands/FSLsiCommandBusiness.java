package ch.supsi.fscli.backend.business.FSCommands;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;
import java.util.stream.Collectors;

public class FSLsiCommandBusiness implements IFSLsiCommandBusiness {

    private static FSLsiCommandBusiness myself;

    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSLsiCommandBusiness() {}

    public static FSLsiCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSLsiCommandBusiness();
        return myself;
    }

    @Override
    public String lsi(String name) {

        DirectoryBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
        if (currentDir == null)
            return "Directory not found";

        // Caso: nessun argomento → lista della directory corrente
        if (name == null || name.isBlank()) {
            return currentDir.getContent().stream()
                    .map(INode::getName)
                    .distinct()
                    .collect(Collectors.joining("\n"));
        }

        // 1. Ricerca locale nella directory corrente
        Optional<INode> local = currentDir.getContent().stream()
                .filter(n -> n.getName().equals(name))
                .findFirst();

        if (local.isPresent()) {
            INode found = local.get();
            if (found.getType() != NodeType.DIRECTORY)
                return "That is not a directory";

            DirectoryBusiness d = (DirectoryBusiness) found;
            return d.getContent().stream()
                    .map(INode::getName)
                    .distinct()
                    .collect(Collectors.joining("\n"));
        }

        // 2. fallback: uso PathSolver per path assoluti/relativi
        Optional<INode> targetNodeOpt = PathSolver.resolvePath(name);
        if (targetNodeOpt.isEmpty())
            return "Directory not found";

        INode node = targetNodeOpt.get();

        if (node.getType() != NodeType.DIRECTORY)
            return "That is not a directory";

        DirectoryBusiness targetDir = (DirectoryBusiness) node;

        return targetDir.getContent().stream()
                .map(INode::getName)
                .distinct()
                .collect(Collectors.joining("\n"));
    }


}
