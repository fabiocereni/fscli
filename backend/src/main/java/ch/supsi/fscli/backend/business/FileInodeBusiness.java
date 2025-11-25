package ch.supsi.fscli.backend.business;

public class FileInodeBusiness extends Inode {

    private boolean isSoftLink;
    private String linkPath;

    public FileInodeBusiness(long id) {
        super(id, InodeType.FILE);
    }


    public boolean isSoftLink() {
        return isSoftLink;
    }

    public void setSoftLink(boolean softLink) {
        isSoftLink = softLink;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }
}