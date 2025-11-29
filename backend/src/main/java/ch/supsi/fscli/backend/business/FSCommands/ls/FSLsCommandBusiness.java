//package ch.supsi.fscli.backend.business.FSCommands.ls;
//
//import ch.supsi.fscli.backend.business.DirectoryInodeBusiness;
//import ch.supsi.fscli.backend.business.IFSStateBusiness;
//import ch.supsi.fscli.backend.business.Inode;
//import ch.supsi.fscli.backend.business.InodeType;
//import ch.supsi.fscli.backend.business.PathSolver;
//import com.google.inject.Inject;
//import com.google.inject.Singleton;
//
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Singleton
//public class FSLsCommandBusiness implements IFSLsCommandBusiness {
//
//    private final IFSStateBusiness stateBusiness;
//    private final PathSolver pathSolver;
//
//    @Inject
//    public FSLsCommandBusiness(IFSStateBusiness stateBusiness, PathSolver pathSolver) {
//        this.stateBusiness = stateBusiness;
//        this.pathSolver = pathSolver;
//    }
//
//    @Override
//    public String ls(String object, boolean id) {
//
//        DirectoryInodeBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
//
//        java.util.function.Function<Inode, String> format = i ->
//                id ? (i.getName() + " [" + i.getId() + "]") : i.getName();
//
//        // Caso: nessun argomento → lista directory corrente
//        if (object == null || object.isBlank()) {
//            return currentDir.getEntries().entrySet().stream()
//                    .map(e -> id ? e.getKey() + " [" + e.getValue().getId() + "]"
//                            : e.getKey())
//                    .sorted()
//                    .collect(Collectors.joining("\n"));
//        }
//
//        // Risoluzione path
//        Optional<Inode> targetOpt = pathSolver.resolvePath(object);
//        if (targetOpt.isEmpty())
//            return "Directory not found";
//
//        Inode target = targetOpt.get();
//
//        if (target.getType() != InodeType.DIRECTORY)
//            return "Not a directory";
//
//        DirectoryInodeBusiness dir = (DirectoryInodeBusiness) target;
//
//        return dir.getEntries().entrySet().stream()
//                .map(e -> id ? e.getKey() + " [" + e.getValue().getId() + "]"
//                        : e.getKey())
//                .sorted()
//                .collect(Collectors.joining("\n"));
//    }
//}
