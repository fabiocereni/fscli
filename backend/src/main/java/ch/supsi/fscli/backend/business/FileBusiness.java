package ch.supsi.fscli.backend.business;

public class FileBusiness implements IFileBusiness {
    private final Node node;
    private boolean softLink = false;
    private String linkPath;

    public FileBusiness(IDirectoryBusiness parent, String name) {
        this.node = new Node(parent, name);
        if (parent != null)
            parent.addContent(this);
    }

    public FileBusiness(String newName, FileBusiness original) {
        this.node = original.node;
        this.node.incrementLinkCount();
        this.setName(newName);
    }



    @Override
    public String getName() {
        return node.getName();
    }

    @Override
    public void setName(String name) {
        node.setName(name);
    }

    @Override
    public IDirectoryBusiness getParent() {
        return node.getParent();
    }

    @Override
    public void setParent(IDirectoryBusiness parent) {
        node.setParent(parent);
    }

    @Override
    public NodeType getType() {
        return NodeType.FILE;
    }

    @Override
    public String toString() {
        return "File(" + getName() + ")";
    }


    @Override
    public boolean isSoftLink() {
        return softLink;
    }

    @Override
    public void setSoftLink(boolean value) {
        this.softLink = value;
    }

    @Override
    public String getLinkPath() {
        return this.linkPath;
    }

    @Override
    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }


    @Override
    public int getLinkCounter() {
        return this.node.getLinkCount();
    }

    @Override
    public void incrementLinkCount() {
        this.node.incrementLinkCount();
    }

    @Override
    public void decrementLinkCount() {
        this.node.decrementLinkCount();
    }
}
