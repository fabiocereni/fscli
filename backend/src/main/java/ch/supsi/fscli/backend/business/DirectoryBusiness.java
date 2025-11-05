package ch.supsi.fscli.backend.business;

public class DirectoryBusiness extends FSElementBusiness implements IDirectoryBusiness {

    public DirectoryBusiness(FSElementBusiness parent, String name) {
        super(parent, name);
    }

    @Override
    public void pwd() {
        System.out.println("message from directory: pwd");
    }

    @Override
    public DirectoryBusiness mkdir() {
        System.out.println("message from directory: mkdir");
        return null;
    }
}
