package ch.supsi.fscli.backend.business;

public interface INode {
    String getName();
    void setName(String name);
    IDirectoryBusiness getParent();
    void setParent(IDirectoryBusiness parent);
    NodeType getType();
    boolean isSoftLink();
    void setSoftLink(boolean value);
    String getLinkPath();
    void setLinkPath(String linkPath);
}