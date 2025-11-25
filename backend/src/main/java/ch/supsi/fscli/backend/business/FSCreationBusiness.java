package ch.supsi.fscli.backend.business;

public class FSCreationBusiness implements IFSCreationBusiness {

    private final FileSystem fileSystem = FileSystem.getInstance();
    private final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    private static FSCreationBusiness myself;

    private FSCreationBusiness() {}

    public static FSCreationBusiness getInstance() {
        if (myself == null)
            myself = new FSCreationBusiness();

        return myself;
    }

    @Override
    public void newfs() {
        DirectoryInodeBusiness root = fileSystem.getRoot();
        this.ifsStateBusiness.setRoot(root);
        this.ifsStateBusiness.setCurrentWorkingDirectory(root);
        System.out.println("NEW FILESYSTEM CREATED");
    }
}
