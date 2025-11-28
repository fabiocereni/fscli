package ch.supsi.fscli.backend.business.FSCommands.mv;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;

public class FSMvCommandBusiness implements IFSMvCommandBusiness {

    private static FSMvCommandBusiness myself;
    private final FileSystem fileSystem = FileSystem.getInstance();

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

        DirectoryInodeBusiness sourceParent = PathSolver.extractParentDirectory(source);
        if (sourceParent == null)
            return false;

        String sourceName = PathSolver.extractFileName(source);
        Inode sourceNode = sourceParent.getEntry(sourceName);
        if (sourceNode == null)
            return false;

        DirectoryInodeBusiness destinationParentDir;
        String destinationName;

        Optional<Inode> destinationNodeOpt = PathSolver.resolvePath(destination);
        // mv file -> directory
        if (destinationNodeOpt.isPresent() && destinationNodeOpt.get() instanceof DirectoryInodeBusiness directoryDestination) {
            destinationParentDir = directoryDestination;
            destinationName = sourceName;
        } else {
            // cambio nome
            destinationParentDir = PathSolver.extractParentDirectory(destination);
            destinationName = PathSolver.extractFileName(destination);
        }

        if (destinationParentDir == null || destinationName.isBlank())
            return false;

        if (PathSolver.nameAlreadyExists(destinationParentDir, destinationName))
            return false;

        if (sourceNode instanceof DirectoryInodeBusiness sourceDir) {
            if (sourceDir == destinationParentDir || isDescendant(sourceDir, destinationParentDir)) {
                return false;
            }
        }

        sourceParent.removeEntry(sourceName);
        destinationParentDir.addEntry(destinationName, sourceNode);

        if (sourceNode instanceof DirectoryInodeBusiness dirToMove) {
            dirToMove.addEntry("..", destinationParentDir);

            sourceParent.decLinkCount();
            destinationParentDir.incLinkCount();
        }

        return true;
    }

    // controlla se la cartella in cui voglio spostare i file/cartella si trova dentro la cartella che sto spostando
    private boolean isDescendant(DirectoryInodeBusiness sourceDir, DirectoryInodeBusiness destParent) {
        DirectoryInodeBusiness current = destParent;

        while (current != null) {
            if (current == sourceDir) {
                return true;
            }
            Inode parentInode = current.getEntry("..");
            if (parentInode == current) {
                break;
            }
            if (parentInode instanceof DirectoryInodeBusiness parentDir) {
                current = parentDir;
            } else {
                break;
            }
        }
        return false;
    }

}
