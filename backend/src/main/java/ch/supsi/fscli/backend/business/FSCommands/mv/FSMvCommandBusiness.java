package ch.supsi.fscli.backend.business.FSCommands.mv;

import ch.supsi.fscli.backend.business.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.Inode;
import ch.supsi.fscli.backend.business.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class FSMvCommandBusiness implements IFSMvCommandBusiness {

    private final PathSolver pathSolver;

    @Inject
    public FSMvCommandBusiness(PathSolver pathSolver) {
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean mv(String source, String destination) {

        if (source == null || destination == null || source.isBlank() || destination.isBlank())
            return false;

        DirectoryInodeBusiness sourceParent = pathSolver.extractParentDirectory(source);
        if (sourceParent == null)
            return false;

        String sourceName = pathSolver.extractFileName(source);
        Inode sourceNode = sourceParent.getEntry(sourceName);
        if (sourceNode == null)
            return false;

        DirectoryInodeBusiness destinationParentDir;
        String destinationName;

        Optional<Inode> destinationNodeOpt = pathSolver.resolvePath(destination);

        // mv file -> existing directory
        if (destinationNodeOpt.isPresent()
                && destinationNodeOpt.get() instanceof DirectoryInodeBusiness directoryDestination) {
            destinationParentDir = directoryDestination;
            destinationName = sourceName;
        } else {
            // rename or move into a parent
            destinationParentDir = pathSolver.extractParentDirectory(destination);
            destinationName = pathSolver.extractFileName(destination);
        }

        if (destinationParentDir == null || destinationName.isBlank())
            return false;

        if (pathSolver.nameAlreadyExists(destinationParentDir, destinationName))
            return false;

        // prevent moving a directory inside itself or its subtree
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
