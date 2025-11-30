package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.application.IFSDataReaderApplication;
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
