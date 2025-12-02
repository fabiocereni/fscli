package ch.supsi.fscli.backend.business.filesystem.FSCommands.ls;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class FSLsCommandBusiness implements IFSLsCommandBusiness {

    private final IFSStateBusiness stateBusiness;
    private final PathSolver pathSolver;

    @Inject
    public FSLsCommandBusiness(IFSStateBusiness stateBusiness, PathSolver pathSolver) {
        this.stateBusiness = stateBusiness;
        this.pathSolver = pathSolver;
    }

    @Override
    public String ls(String path, boolean showInodeIds) {
        DirectoryInodeBusiness targetDir;

        // Caso 1: ls senza argomenti -> usa CWD
        if (path == null || path.isBlank()) {
            targetDir = stateBusiness.getCurrentWorkingDirectory();
        } else {
            // Caso 2: ls <path> -> risolvi il path
            Optional<Inode> targetOpt = pathSolver.resolvePath(path);

            if (targetOpt.isEmpty()) {
                return "ls: cannot access '" + path + "': No such file or directory";
            }

            Inode targetNode = targetOpt.get();

            // Se è un file, mostriamo solo il nome (comportamento standard ls)
            // Oppure ritorniamo errore se vuoi simulare rigidamente una lista di directory
            if (targetNode.getType() != InodeType.DIRECTORY) {
                // Opzione A: Mostra info file
                return formatEntry(pathSolver.extractFileName(path), targetNode, showInodeIds);
                // Opzione B: Errore (come nel tuo codice commentato)
                // return "ls: " + path + ": Not a directory";
            }

            targetDir = (DirectoryInodeBusiness) targetNode;
        }

        // Generazione Output
        if (targetDir.getEntries().isEmpty()) {
            return "";
        }

        return targetDir.getEntries().entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().compareToIgnoreCase(e2.getKey())) // Ordine alfabetico
                .map(entry -> formatEntry(entry.getKey(), entry.getValue(), showInodeIds))
                .collect(Collectors.joining("\n"));
    }

    private String formatEntry(String name, Inode inode, boolean showId) {
        if (showId) {
            return name + " [" + inode.getId() + "]";
        }
        return name;
    }
}