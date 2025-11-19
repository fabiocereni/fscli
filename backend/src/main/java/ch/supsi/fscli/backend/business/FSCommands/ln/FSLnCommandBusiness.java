package ch.supsi.fscli.backend.business.FSCommands.ln;

import ch.supsi.fscli.backend.business.*;
import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;
import ch.supsi.fscli.backend.exception.MyFileNotFoundException;

import java.util.Optional;

public class FSLnCommandBusiness implements IFSLnCommandBusiness {


//    public IDirectoryBusiness extractParentDirectory(String path) {
//
//
//
//    }

    @Override
    public boolean ln(String target, String linkName) throws DirectoryNotFoundException,
                                                             MyFileNotFoundException, NodeAlreadyExistsException {

        // target
        Optional<INode> originalOpt = PathSolver.resolvePath(target);
        if (originalOpt.isEmpty())
            throw new MyFileNotFoundException("ln: target file does not exist");

        INode original = originalOpt.get();
        if (original.getType() != NodeType.FILE)
            throw new MyFileNotFoundException("ln: target must be a file");


        // directory padre del link
        int lastSlash = linkName.lastIndexOf('/');
        String newName;
        IDirectoryBusiness parentDir;

        if (lastSlash < 0) {
            // path relativo senza directory → parent = CWD
            parentDir = FSStateBusiness.getInstance().getCurrentWorkingDirectory();
            newName = linkName;

        } else {
            newName = linkName.substring(lastSlash + 1);
            String parentPath = (lastSlash == 0) ? "/" : linkName.substring(0, lastSlash);

            // risolve la cartella padre
            Optional<INode> parentOpt = PathSolver.resolvePath(parentPath);
            if (parentOpt.isEmpty() || parentOpt.get().getType() != NodeType.DIRECTORY)
                throw new DirectoryNotFoundException("ln: directory does not exist");

            parentDir = (IDirectoryBusiness) parentOpt.get();
        }

        // 4. IL LINK NON DEVE ESISTERE
        if (PathSolver.nameAlreadyExists(parentDir, newName))
            throw new NodeAlreadyExistsException("ln: file already exists");

        // 5. CREA L’HARDLINK
        FileBusiness newLink = new FileBusiness(parentDir, newName);


        // aggiunge nella directory
        parentDir.addContent(newLink);

        System.out.println("Hardlink created: " + newName);
        return true;
    }




    @Override
    public boolean lns(String target, String linkName) throws DirectoryNotFoundException, NodeAlreadyExistsException {

        // 1. Risolvo il path del target (PUÒ non esistere subito nei softlink: esiste al momento dell'uso)
        //   → Ma nel tuo modello serve comunque un file esistente
        Optional<INode> targetOpt = PathSolver.resolvePath(target);
        if (targetOpt.isEmpty())
            throw new DirectoryNotFoundException("ln: softlink target does not exist");

        // 2. Estraggo la directory padre + nome del nuovo link
        int lastSlash = linkName.lastIndexOf('/');
        String newName;
        IDirectoryBusiness parentDir;

        if (lastSlash < 0) {
            // path relativo senza '/'
            parentDir = FSStateBusiness.getInstance().getCurrentWorkingDirectory();
            newName = linkName;

        } else {
            newName = linkName.substring(lastSlash + 1);
            String parentPath = (lastSlash == 0) ? "/" : linkName.substring(0, lastSlash);

            Optional<INode> parentOpt = PathSolver.resolvePath(parentPath);
            if (parentOpt.isEmpty() || parentOpt.get().getType() != NodeType.DIRECTORY)
                throw new DirectoryNotFoundException("ln: cannot create link, directory does not exist");

            parentDir = (IDirectoryBusiness) parentOpt.get();
        }

        // 3. Controllo che non esista già un file con quel nome
        if (PathSolver.nameAlreadyExists(parentDir, newName))
            throw new NodeAlreadyExistsException("ln: target already exists");

        // 4. Creo softlink
        FileBusiness softLink = new FileBusiness(parentDir, newName);
        softLink.setSoftLink(true);
        softLink.setLinkPath(target); // salva il path originale

        parentDir.addContent(softLink);

        System.out.println("Softlink created: " + newName);
        return true;
    }


}
