package ch.supsi.fscli.backend.business;

abstract class Inode {
    private final long id;
    private final InodeType type;
    private int linkCount;

    protected Inode(long id, InodeType type) {
        this.id = id;
        this.type = type;
        this.linkCount = 0;
    }

    public long getId() {
        return id;
    }

    public InodeType getType() {
        return type;
    }

    public int getLinkCount() {
        return linkCount;
    }

    public void incLinkCount() {
        linkCount++;
    }

    public void decLinkCount() {
        linkCount--;
    }


}



