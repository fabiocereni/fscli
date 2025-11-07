package ch.supsi.fscli.backend.business;

public class FSCreationBusiness implements IFSCreationBusiness {

    private static FSCreationBusiness myself;

    private FSCreationBusiness() {}

    public static FSCreationBusiness getInstance() {
        if (myself == null)
            myself = new FSCreationBusiness();

        return myself;
    }

    @Override
    public void newfs() {
        IDirectoryBusiness root = new DirectoryBusiness(null, "root");
        System.out.println("ROOT CREATED");
        System.out.println(root.getName());
    }
}
