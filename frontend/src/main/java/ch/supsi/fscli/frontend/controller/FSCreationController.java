package ch.supsi.fscli.frontend.controller;


import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationController implements IFSCreationController {

    @Inject
    private FSCreationDirector fsCreationDirector;

    @Inject
    private IFSStateModel ifsStateModel;


    @Override
    public void createFileSystem() {
        this.fsCreationDirector.createFileSystem();
        this.ifsStateModel.setCloseable(false);
    }
}