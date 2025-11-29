package ch.supsi.fscli.frontend.model;


import ch.supsi.fscli.backend.application.IFSDataWriterApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverModel implements IFSDataSaverModel {

    private final IFSDataWriterApplication ifsDataWriterApplication;

    @Inject
    public FSDataSaverModel(IFSDataWriterApplication ifsDataWriterApplication) {
        this.ifsDataWriterApplication = ifsDataWriterApplication;
    }

    @Override
    public void save(Path path) {
        this.ifsDataWriterApplication.save(path);
    }

    @Override
    public void save() {
        this.ifsDataWriterApplication.save();
    }
}