package ch.supsi.fscli.backend.application.persistence;


import ch.supsi.fscli.backend.business.persistence.IFSDataReaderBusiness;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;

@Singleton
public class FSDataReaderApplication implements IFSDataReaderApplication {

    private final IFSDataReaderBusiness fsDataReaderBusiness;

    @Inject
    public FSDataReaderApplication(IFSDataReaderBusiness fsDataReaderBusiness) {
        this.fsDataReaderBusiness = fsDataReaderBusiness;
    }

    @Override
    public void reader(File file) {
        this.fsDataReaderBusiness.reader(file);
    }
}
