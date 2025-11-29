package ch.supsi.fscli.backend.business.FSCommands.ln;

import ch.supsi.fscli.backend.business.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.FileInodeBusiness;
import ch.supsi.fscli.backend.business.FileSystem;
import ch.supsi.fscli.backend.business.Inode;
import ch.supsi.fscli.backend.business.InodeType;
import ch.supsi.fscli.backend.business.PathSolver;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class FSLnCommandBusiness implements IFSLnCommandBusiness {

    private final FileSystem fileSystem;
    private final PathSolver pathSolver;

    @Inject
    public FSLnCommandBusiness(FileSystem fileSystem, PathSolver pathSolver) {
        this.fileSystem = fileSystem;
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean ln(String target, String linkName) {

        Optional<Inode> targetOpt = pathSolver.resolvePath(target);
        if (targetOpt.isEmpty())
            throw new IllegalArgumentException("ln: target file does not exist");

        Inode targetNode = targetOpt.get();
        if (targetNode.getType() != InodeType.FILE)
            throw new IllegalArgumentException("ln: target is not a file");

        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory(linkName);
        if (parent == null)
            throw new IllegalArgumentException("ln: parent directory does not exist");

        String newName = pathSolver.extractFileName(linkName);

        if (pathSolver.nameAlreadyExists(parent, newName))
            throw new IllegalArgumentException("ln: file with same name already exists");

        parent.addEntry(newName, targetNode);

        targetNode.incLinkCount();

        return true;
    }

    @Override
    public boolean lns(String target, String linkName)
            throws DirectoryNotFoundException, NodeAlreadyExistsException {

        Optional<Inode> targetOpt = pathSolver.resolvePath(target);
        if (targetOpt.isEmpty())
            throw new DirectoryNotFoundException("ln: softlink target does not exist");

        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory(linkName);
        if (parent == null)
            throw new DirectoryNotFoundException("ln: parent directory does not exist");

        String newName = pathSolver.extractFileName(linkName);

        if (pathSolver.nameAlreadyExists(parent, newName))
            throw new NodeAlreadyExistsException("ln: file with same name already exists");

        FileInodeBusiness softLink = fileSystem.createFile();
        softLink.setSoftLink(true);
        softLink.setLinkPath(target);

        parent.addEntry(newName, softLink);

        return true;
    }
}
