package ch.supsi.fscli.backend.business;

public class File extends FSElement implements IFile {

    public File(FSElement parent, String name){
        super(parent, name);
    }


    public static FSElement getParentDirectory(IDirectory directory) {
        return new FSElement(directory.getParent(), directory.getName());
    }

    @Override
    public File touch(String name) {
        System.out.println("message from file: touch");
        return null;
    }

    @Override
    public void pwd() {
        System.out.println("message from file: pwd");
    }
}
