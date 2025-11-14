package ch.supsi.fscli.backend.business;

public class FileBusiness implements IFileBusiness {
    private final Node node;

    public FileBusiness(IDirectoryBusiness parent, String name) {
        this.node = new Node(parent, name,  NodeType.FILE);
        if (parent != null)
            parent.addContent(this);
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
}
