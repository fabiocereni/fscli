package ch.supsi.fscli.backend.business.FSCommands.ls;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;
import java.util.stream.Collectors;

//public class FSLsCommandBusiness implements IFSLsCommandBusiness {
//
//    private static FSLsCommandBusiness myself;
//
//    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();
//
//    private FSLsCommandBusiness() {}
//
//    public static FSLsCommandBusiness getInstance() {
//        if (myself == null)
//            myself = new FSLsCommandBusiness();
//        return myself;
//    }
//
//    @Override
//    public String ls(String name, boolean id) {
//        DirectoryBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
//        if (currentDir == null)
//            return "Directory not found";
//
//        // Funzione per generare output con o senza ID
//        java.util.function.Function<INode, String> formatNode = n ->
//                id ? n.getName() + " [" + n.getInodeId() + "]" : n.getName();
//
//        // Caso: nessun argomento → lista della directory corrente
//        if (name == null || name.isBlank()) {
//            return currentDir.getContent().stream()
//                    .map(formatNode)
//                    .distinct()
//                    .collect(Collectors.joining("\n"));
//        }
//
//        // 1. Ricerca locale nella directory corrente
//        Optional<INode> local = currentDir.getContent().stream()
//                .filter(n -> n.getName().equals(name))
//                .findFirst();
//
//        if (local.isPresent()) {
//            INode found = local.get();
//            if (found.getType() != InodeType.DIRECTORY)
//                return "That is not a directory";
//
//            DirectoryBusiness d = (DirectoryBusiness) found;
//            return d.getContent().stream()
//                    .map(formatNode)
//                    .distinct()
//                    .collect(Collectors.joining("\n"));
//        }
//
//        // 2. fallback: uso PathSolver per path assoluti/relativi
//        Optional<INode> targetNodeOpt = PathSolver.resolvePath(name);
//        if (targetNodeOpt.isEmpty())
//            return "Directory not found";
//
//        INode node = targetNodeOpt.get();
//        if (node.getType() != InodeType.DIRECTORY)
//            return "That is not a directory";
//
//        DirectoryBusiness targetDir = (DirectoryBusiness) node;
//        return targetDir.getContent().stream()
//                .map(formatNode)
//                .distinct()
//                .collect(Collectors.joining("\n"));
//    }
//}
