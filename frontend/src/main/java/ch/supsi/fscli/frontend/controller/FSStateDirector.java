package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.backend.application.FSCreationApplication;
import ch.supsi.fscli.backend.application.FSStateApplication;
import ch.supsi.fscli.backend.application.IFSCreationApplication;
import ch.supsi.fscli.backend.application.IFSStateApplication;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class FSStateDirector implements IFSStateDirector {

    private static FSStateDirector myself;

    private final IFSStateApplication ifsStateApplication = FSStateApplication.getInstance();
    private final IFSCreationApplication ifsCreationApplication = FSCreationApplication.getInstance();

    private final BooleanProperty fileSystemCreated = new SimpleBooleanProperty(false);

    private FSStateDirector() {}

    public static FSStateDirector getInstance() {
        if(myself == null)
            myself = new FSStateDirector();

        return myself;
    }

    @Override
    public void changeSavedStateAndGet() {
        if(this.ifsStateApplication.changeSavedStateAndGet()) {
            this.fileSystemCreated.set(true);
        }

    }

    @Override
    public void createFileSystem() {
        this.ifsCreationApplication.createFileSystem();
        this.fileSystemCreated.set(true);
    }

    @Override
    public BooleanProperty fileSystemCreatedProperty() {
        return fileSystemCreated;
    }
}
