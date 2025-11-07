package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.backend.application.IFSCreationApplication;
import ch.supsi.fscli.backend.application.IFSStateApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@Singleton
public class FSStateDirector implements IFSStateDirector {

    private final IFSStateApplication ifsStateApplication;
    private final IFSCreationApplication ifsCreationApplication;

    private final BooleanProperty fileSystemCreated = new SimpleBooleanProperty(false);

    @Inject
    public FSStateDirector(IFSStateApplication ifsStateApplication, IFSCreationApplication ifsCreationApplication) {
        this.ifsStateApplication = ifsStateApplication;
        this.ifsCreationApplication = ifsCreationApplication;
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
