package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.application.FSDataWriterApplication;
import ch.supsi.fscli.backend.application.IFSDataWriterApplication;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverModel implements IFSDataSaverModel {

    private final IFSDataWriterApplication ifsDataWriterApplication = FSDataWriterApplication.getInstance();

    @Override
    public void save(Path path) {
        this.ifsDataWriterApplication.save(path);
    }

    @Override
    public void save() {
        this.ifsDataWriterApplication.save();
    }
}