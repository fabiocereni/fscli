package ch.supsi.fscli.backend.business;

public class FileInodeBusiness extends Inode {

    public FileInodeBusiness(long id) {
        super(id, InodeType.FILE);
    }
}