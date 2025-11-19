package ch.supsi.fscli.backend.business;

public interface IFileBusiness extends INode {
    int getLinkCounter();
    void incrementLinkCount();
    void decrementLinkCount();
}