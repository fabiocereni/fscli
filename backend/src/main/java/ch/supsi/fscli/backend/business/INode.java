package ch.supsi.fscli.backend.business;

public interface INode {
    String getName();
    void setName(String name);
    IDirectoryBusiness getParent();
    void setParent(IDirectoryBusiness parent);
}