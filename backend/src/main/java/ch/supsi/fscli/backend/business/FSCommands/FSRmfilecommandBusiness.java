package ch.supsi.fscli.backend.business.FSCommands;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;

public class FSRmfilecommandBusiness implements IFSRmfileCommandBusiness {

    private static FSRmfilecommandBusiness myself;

    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSRmfilecommandBusiness() {}

    public static FSRmfilecommandBusiness getInstance() {
        if (myself == null)
            myself = new FSRmfilecommandBusiness();
        return myself;
    }

    @Override
    public boolean rmfile(String name) {

        if (name == null || name.isBlank() || name.equals(".") || name.equals(".."))
            return false;

        DirectoryBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
        if (currentDir == null)
            return false;

        Optional<INode> targetNode = currentDir.getContent().stream()
                .filter(node -> node.getName().equals(name))
                .findFirst();

        if (targetNode.isEmpty()) {
            System.out.println("rmfile: file not found '" + name + "'");
            return false;
        }

        INode target = targetNode.get();
        if (target.getType() != NodeType.FILE) {
            System.out.println("rmfile: not a file: '" + name + "'");
            return false;
        }

        boolean result = currentDir.getContent().remove(target);
        if (result) {
            target.setParent(null);
            return true;
        }

        return false;
    }
}
