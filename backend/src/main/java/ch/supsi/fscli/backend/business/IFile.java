package ch.supsi.fscli.backend.business;

public interface IFile extends FSCommands, IFSElement<FSElement> {
    File touch(String name);
}
