package ch.supsi.fscli.frontend.controller.filesystem.creation;


import ch.supsi.fscli.backend.application.filesystem.creation.IFSCreationApplication;
import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationController implements IFSCreationController {

    @Inject
    private FSCreationDirector fsCreationDirector;

    @Inject
    private IFSStateModel ifsStateModel;

    @Inject
    private LogDirector logDirector;

    @Inject
    private IFSCreationApplication ifsCreationApplication;


    @Override
    public void createFileSystem() {
        this.ifsCreationApplication.createFileSystem();
        this.fsCreationDirector.manageFileSystemCreation();
        this.ifsStateModel.setCloseable(false);
        logDirector.logCreateFS();
    }
}