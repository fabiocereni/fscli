package ch.supsi.fscli.backend.business.filesystem.structure;

public class FileInodeBusiness extends Inode {
    private boolean isSoftLink;
    private String linkPath;

    private FileInodeBusiness() {
        super();
    }

    public FileInodeBusiness(long id) {
        super(id, InodeType.FILE);
    }

    public boolean isSoftLink() {
        return isSoftLink;
    }

    public void setSoftLink(boolean softLink) {
        this.isSoftLink = softLink;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }
}