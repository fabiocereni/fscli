package ch.supsi.fscli.backend.business.FSCommands.ln;

import ch.supsi.fscli.backend.business.*;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;

import java.util.Optional;

//public class FSLnCommandBusiness implements IFSLnCommandBusiness {
//
//
//    @Override
//    public boolean ln(String target, String linkName) {
//        Optional<INode> targetOpt = PathSolver.resolvePath(target);
//        if (targetOpt.isEmpty())
//            throw new IllegalArgumentException("ln: target file does not exist");
//
//        INode targetNode = targetOpt.get();
//        if (targetNode.getType() != InodeType.FILE)
//            throw new IllegalArgumentException("ln: target is not a file");
//
//
//        IDirectoryBusiness parentDir = PathSolver.extractParentDirectory(linkName);
//        if (parentDir == null)
//            throw new IllegalArgumentException("ln: parent directory does not exist");
//
//        String newName = PathSolver.extractFileName(linkName);
//
//
//        if (PathSolver.nameAlreadyExists(parentDir, newName))
//            throw new IllegalArgumentException("ln: file with same name already exists");
//
//
//        new FileBusiness(parentDir, newName);
//
//        System.out.println("Hardlink created: " + newName);
//        return true;
//    }
//
//    @Override
//    public boolean lns(String target, String linkName)
//            throws DirectoryNotFoundException, NodeAlreadyExistsException {
//
//
//        Optional<INode> targetOpt = PathSolver.resolvePath(target);
//        if (targetOpt.isEmpty())
//            throw new DirectoryNotFoundException("ln: softlink target does not exist");
//
//
//        IDirectoryBusiness parentDir = PathSolver.extractParentDirectory(linkName);
//        if (parentDir == null)
//            throw new DirectoryNotFoundException("ln: parent directory does not exist");
//
//        String newName = PathSolver.extractFileName(linkName);
//
//
//        if (PathSolver.nameAlreadyExists(parentDir, newName))
//            throw new NodeAlreadyExistsException("ln: file with same name already exists");
//
//
//        FileBusiness softLink = new FileBusiness(parentDir, newName);
//        softLink.setSoftLink(true);
//        softLink.setLinkPath(target);
//
//        System.out.println("Softlink created: " + newName);
//        return true;
//    }
//
//
//
//}
