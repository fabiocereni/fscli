package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

public class DirectoryBusiness implements IDirectoryBusiness {
    private final Node node;
    private boolean softLink = false;
    private String linkPath;

    @JsonManagedReference
    private final List<INode> content = new ArrayList<>();

    public DirectoryBusiness(IDirectoryBusiness parent, String name) {
        this.node = new Node(parent, name);
        if (parent != null)
            parent.addContent(this);
    }

    @Override
    public void addContent(INode node) {
        content.add(node);
    }

    @Override
    public List<INode> getContent() {
        return content;
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
        return NodeType.DIRECTORY;
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
    public String toString() {
        return "Dir(" + getName() + ")";
    }
}
