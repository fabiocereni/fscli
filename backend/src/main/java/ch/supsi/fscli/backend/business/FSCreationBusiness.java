package ch.supsi.fscli.backend.business;

public class FSCreationBusiness implements IFSCreationBusiness {

    IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    private static FSCreationBusiness myself;

    private FSCreationBusiness() {}

    public static FSCreationBusiness getInstance() {
        if (myself == null)
            myself = new FSCreationBusiness();

        return myself;
    }

    @Override
    public void newfs() {
        if(this.ifsStateBusiness.getRoot() == null) {
            DirectoryBusiness root = new DirectoryBusiness(null, "root");
            this.ifsStateBusiness.setRoot(root);
            this.ifsStateBusiness.setCurrentWorkingDirectory(root);
            System.out.println("ROOT CREATED");
        }
    }
}
