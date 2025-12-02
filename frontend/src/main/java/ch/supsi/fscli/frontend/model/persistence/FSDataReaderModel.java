package ch.supsi.fscli.frontend.model.persistence;

import ch.supsi.fscli.backend.application.persistence.IFSDataReaderApplication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;

@Singleton
public class FSDataReaderModel implements IFSDataReaderModel {

    private final IFSDataReaderApplication fsDataReaderApplication;

    @Inject
    public FSDataReaderModel(IFSDataReaderApplication fsDataReaderApplication) {
        this.fsDataReaderApplication = fsDataReaderApplication;
    }

    @Override
    public void reader(File file) {
        this.fsDataReaderApplication.reader(file);
    }

}
