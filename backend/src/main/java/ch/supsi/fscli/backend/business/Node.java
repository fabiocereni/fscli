package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Node {

    @JsonBackReference
    private IDirectoryBusiness parent;

    private String name;
    private static int inodeCounter = 0;
    private int inodeId;

    public Node(IDirectoryBusiness parent, String name, NodeType type) {
        this.parent = parent;
        this.name = name;
        this.inodeId = ++inodeCounter;
    }

    public IDirectoryBusiness getParent() { return parent; }

    public void setParent(IDirectoryBusiness parent) { this.parent = parent; }

    public String getName() { return parent == null ? "root" : name; }

    public void setName(String name) {
        if (parent == null)
            System.out.println("cannot rename root");
        else
            this.name = name;
    }

    public int getInodeId() { return inodeId; }
}
