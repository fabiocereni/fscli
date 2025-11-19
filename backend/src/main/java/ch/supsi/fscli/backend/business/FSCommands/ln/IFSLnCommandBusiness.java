package ch.supsi.fscli.backend.business.FSCommands.ln;

import ch.supsi.fscli.backend.exception.DirectoryNotFoundException;
import ch.supsi.fscli.backend.exception.MyFileNotFoundException;
import ch.supsi.fscli.backend.exception.NodeAlreadyExistsException;

public interface IFSLnCommandBusiness {
    boolean ln(String target, String linkName) throws DirectoryNotFoundException, MyFileNotFoundException,
            NodeAlreadyExistsException;
    boolean lns(String target, String linkName) throws DirectoryNotFoundException, NodeAlreadyExistsException;
}
