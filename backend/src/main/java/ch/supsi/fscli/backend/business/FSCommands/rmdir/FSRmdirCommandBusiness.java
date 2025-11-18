package ch.supsi.fscli.backend.business.FSCommands.rmdir;

import ch.supsi.fscli.backend.business.*;

import java.util.Iterator;

public class FSRmdirCommandBusiness implements IFSRmdirCommandBusiness {

    private static FSRmdirCommandBusiness myself;
    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSRmdirCommandBusiness() {}

    public static FSRmdirCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSRmdirCommandBusiness();

        return myself;
    }

    @Override
    public boolean rmdir(String name) {
        if (name == null || name.isBlank())
            return false;

        DirectoryBusiness current = stateBusiness.getCurrentWorkingDirectory();

        Iterator<INode> it = current.getContent().iterator();
        while (it.hasNext()) {
            INode i = it.next();
            if (i.getName().equals(name) && i.getType() == NodeType.DIRECTORY) {
                DirectoryBusiness dir = (DirectoryBusiness) i;
                if (!dir.getContent().isEmpty()) {
                    return false; // non vuota
                }
                System.out.println("Checking node: " + i.getName() + ", type=" + i.getType());
                it.remove(); // rimuove correttamente dalla lista
                return true;
            }
        }
        return false;
    }
}
