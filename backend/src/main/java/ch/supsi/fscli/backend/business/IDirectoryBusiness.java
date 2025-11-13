package ch.supsi.fscli.backend.business;

import java.util.List;

public interface IDirectoryBusiness extends INode {
    void addContent(INode node);
    List<INode> getContent();
}
