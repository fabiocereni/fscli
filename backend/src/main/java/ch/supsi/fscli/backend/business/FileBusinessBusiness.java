package ch.supsi.fscli.backend.business;

public class FileBusinessBusiness extends FSElementBusiness implements IFileBusiness {

    public FileBusinessBusiness(FSElementBusiness parent, String name){
        super(parent, name);
    }


    public static FSElementBusiness getParentDirectory(IDirectoryBusiness directory) {
        return new FSElementBusiness(directory.getParent(), directory.getName());
    }

    @Override
    public FileBusinessBusiness touch(String name) {
        System.out.println("message from file: touch");
        return null;
    }

    @Override
    public void pwd() {
        System.out.println("message from file: pwd");
    }
}
