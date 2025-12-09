package ch.supsi.fscli.backend.business.filesystem.FSCommands.ls;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class FSLsCommandBusiness implements IFSLsCommandBusiness {

    private final FileSystem fileSystem;
    private final PathSolver pathSolver;

    @Inject
    public FSLsCommandBusiness(FileSystem fileSystem, PathSolver pathSolver) {
        this.fileSystem = fileSystem;
        this.pathSolver = pathSolver;
    }

    @Override
    public CommandResult ls(String path, boolean showInodeIds) {
        DirectoryInodeBusiness targetDir;

        // Caso 1: ls senza argomenti -> usa CWD
        if (path == null || path.isBlank()) {
            targetDir = fileSystem.getCurrentWorkingDirectory();
        } else {
            // Caso 2: ls <path> -> risolvi il path
            Optional<Inode> targetOpt = pathSolver.resolvePath(path);

            if (targetOpt.isEmpty()) {
                return new CommandResult("label.wrongLsUse2", true);
            }

            Inode targetNode = targetOpt.get();

            // Se è un file, mostriamo solo il nome (comportamento standard ls)
            // Oppure ritorniamo errore se vuoi simulare rigidamente una lista di directory
            if (targetNode.getType() != InodeType.DIRECTORY) {
                // Opzione A: Mostra info file
                return new CommandResult(formatEntry(pathSolver.extractFileName(path), targetNode, showInodeIds), false);
                // Opzione B: Errore (come nel tuo codice commentato)
                // return "ls: " + path + ": Not a directory";
            }

            targetDir = (DirectoryInodeBusiness) targetNode;
        }

        // Generazione Output
        if (targetDir.getEntries().isEmpty()) {
            return null;
        }

        return new CommandResult(targetDir.getEntries().entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().compareToIgnoreCase(e2.getKey())) // Ordine alfabetico
                .map(entry -> formatEntry(entry.getKey(), entry.getValue(), showInodeIds))
                .collect(Collectors.joining("\n")), false);
    }

    private String formatEntry(String name, Inode inode, boolean showId) {
        if (showId) {
            return name + " [" + inode.getId() + "]";
        }
        return name;
    }
}