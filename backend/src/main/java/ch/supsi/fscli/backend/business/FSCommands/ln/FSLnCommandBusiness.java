package ch.supsi.fscli.backend.business.FSCommands.ln;

import ch.supsi.fscli.backend.business.*;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;

import java.util.Optional;

public class FSLnCommandBusiness implements IFSLnCommandBusiness {

    private final IFSStateBusiness fsState = FSStateBusiness.getInstance();
    private final FileSystem fileSystem = FileSystem.getInstance();

    @Override
    public boolean ln(String target, String linkName) {

        // 1) risolvi target
        Optional<Inode> targetOpt = PathSolver.resolvePath(target);
        if (targetOpt.isEmpty())
            throw new IllegalArgumentException("ln: target file does not exist");

        Inode targetNode = targetOpt.get();
        if (targetNode.getType() != InodeType.FILE)
            throw new IllegalArgumentException("ln: target is not a file");

        // 2) trova directory padre del nuovo link
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory(linkName);
        if (parent == null)
            throw new IllegalArgumentException("ln: parent directory does not exist");

        String newName = PathSolver.extractFileName(linkName);

        // 3) controlla conflitti
        if (PathSolver.nameAlreadyExists(parent, newName))
            throw new IllegalArgumentException("ln: file with same name already exists");

        // 4) HARD LINK = aggiungere nella dir un puntamento allo STESSO inode
        parent.addEntry(newName, targetNode);

        System.out.println("Hardlink created: " + newName);
        return true;
    }

    @Override
    public boolean lns(String target, String linkName)
            throws DirectoryNotFoundException, NodeAlreadyExistsException {

        // 1) risolvi target
        Optional<Inode> targetOpt = PathSolver.resolvePath(target);
        if (targetOpt.isEmpty())
            throw new DirectoryNotFoundException("ln: softlink target does not exist");

        // 2) directory padre
        DirectoryInodeBusiness parent = PathSolver.extractParentDirectory(linkName);
        if (parent == null)
            throw new DirectoryNotFoundException("ln: parent directory does not exist");

        String newName = PathSolver.extractFileName(linkName);

        // 3) conflitti
        if (PathSolver.nameAlreadyExists(parent, newName))
            throw new NodeAlreadyExistsException("ln: file with same name already exists");

        // 4) SOFT LINK = creare inode nuovo, con flag softlink
        FileInodeBusiness softLink = fileSystem.createFile();
        softLink.setSoftLink(true);
        softLink.setLinkPath(target);

        // 5) aggiungerlo nella cartella
        parent.addEntry(newName, softLink);

        System.out.println("Softlink created: " + newName);
        return true;
    }
}
