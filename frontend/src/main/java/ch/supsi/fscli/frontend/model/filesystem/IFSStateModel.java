package ch.supsi.fscli.frontend.model.filesystem;

public interface IFSStateModel {
    boolean isCloseable();
    void setCloseable(boolean newState);
}
