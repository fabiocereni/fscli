package ch.supsi.fscli.backend.business;

public interface IDirectory extends FSCommands, IFSElement<FSElement> {
    Directory mkdir();
}
