package ch.supsi.fscli.backend.business.filesystem.FSCommands.ln;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
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
    public String lns(String target, String linkName) {

        Optional<Inode> targetOpt = pathSolver.resolvePath(target);
        if (targetOpt.isEmpty())
            return "label.wrongLnUse2";

        DirectoryInodeBusiness parent = pathSolver.extractParentDirectory(linkName);
        if (parent == null)
            return "label.wrongLnUse3";

        String newName = pathSolver.extractFileName(linkName);

        if (pathSolver.nameAlreadyExists(parent, newName))
            return "label.wrongLnUse4";

        FileInodeBusiness softLink = fileSystem.createFile();
        softLink.setSoftLink(true);
        softLink.setLinkPath(target);

        parent.addEntry(newName, softLink);

        return "";
    }
}
