package ch.supsi.fscli.backend.business.FSCommands.rmfile;

import ch.supsi.fscli.backend.business.*;


public class FSRmfilecommandBusiness implements IFSRmfileCommandBusiness {

    private static FSRmfilecommandBusiness myself;
    private final FileSystem fileSystem = FileSystem.getInstance();

    private FSRmfilecommandBusiness() {}

    public static FSRmfilecommandBusiness getInstance() {
        if (myself == null)
            myself = new FSRmfilecommandBusiness();
        return myself;
    }

    @Override
    public boolean rmfile(String name) {

        if (name == null || name.isBlank())
            return false;

        DirectoryInodeBusiness parentDir = PathSolver.extractParentDirectory(name);
        if (parentDir == null)
            return false;

        String fileName = PathSolver.extractFileName(name);
        if (fileName.isBlank())
            return false;

        Inode target = parentDir.getEntry(fileName);
        if (target == null)
            return false;

        if (target.getType() == InodeType.DIRECTORY)
            return false;

        parentDir.removeEntry(fileName);
        target.decLinkCount();

        return true;
    }
}
