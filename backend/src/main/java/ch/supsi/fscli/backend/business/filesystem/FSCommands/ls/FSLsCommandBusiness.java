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

        if (path == null || path.isBlank()) {
            targetDir = fileSystem.getCurrentWorkingDirectory();
        } else {
            Optional<Inode> targetOpt = pathSolver.resolvePath(path);

            if (targetOpt.isEmpty()) {
                return new CommandResult("label.wrongLsUse2", true);
            }

            Inode targetNode = targetOpt.get();

            if (targetNode.getType() != InodeType.DIRECTORY) {
                return new CommandResult(formatEntry(pathSolver.extractFileName(path), targetNode, showInodeIds), false);
            }

            targetDir = (DirectoryInodeBusiness) targetNode;
        }

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