package ch.supsi.fscli.frontend.controller;

import javafx.beans.property.BooleanProperty;

public interface IFSStateDirector extends EventHandler {
    void changeSavedStateAndGet();
    void createFileSystem();
    BooleanProperty fileSystemCreatedProperty();
}
