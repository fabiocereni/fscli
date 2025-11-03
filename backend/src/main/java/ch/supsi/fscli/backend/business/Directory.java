package ch.supsi.fscli.backend.business;

public class Directory extends FSElement implements IDirectory {

    public Directory(FSElement parent, String name) {
        super(parent, name);
    }

    @Override
    public void pwd() {
        System.out.println("message from directory: pwd");
    }

    @Override
    public Directory mkdir() {
        System.out.println("message from directory: mkdir");
        return null;
    }
}
