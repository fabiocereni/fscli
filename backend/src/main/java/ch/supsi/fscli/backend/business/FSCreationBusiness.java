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
            this.ifsStateBusiness.setRoot(new DirectoryBusiness(null, "root"));
            System.out.println("ROOT CREATED");
        }
    }
}
