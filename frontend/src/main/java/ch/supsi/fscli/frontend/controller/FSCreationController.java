package ch.supsi.fscli.frontend.controller;


import ch.supsi.fscli.frontend.director.FSStateDirector;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationController implements IFSCreationController {

    @Inject
    private FSStateDirector fsStateDirector;


    @Override
    public void createFileSystem() {
        this.fsStateDirector.createFileSystem();
    }
}