package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

public class DirectoryBusiness implements IDirectoryBusiness {
    private final Node node;

    @JsonManagedReference
    private final List<INode> content = new ArrayList<>();

    public DirectoryBusiness(IDirectoryBusiness parent, String name) {
        this.node = new Node(parent, name, NodeType.DIRECTORY);
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
    public String toString() {
        return "Dir(" + getName() + ")";
    }
}
