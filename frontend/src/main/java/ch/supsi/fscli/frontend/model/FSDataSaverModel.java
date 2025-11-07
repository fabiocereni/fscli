package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.application.FSDataWriterApplication;
import ch.supsi.fscli.backend.application.IFSDataWriterApplication;
import ch.supsi.fscli.backend.business.AbstractFSBusiness;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverModel implements IFSDataSaverModel {

    private final IFSDataWriterApplication ifsDataWriterApplication = FSDataWriterApplication.getInstance();

    @Override
    public void save(Path path, AbstractFSBusiness abstractFSBusiness) {
        this.ifsDataWriterApplication.save(path, abstractFSBusiness);
    }

    @Override
    public void save(AbstractFSBusiness abstractFSBusiness) {
        this.ifsDataWriterApplication.save(abstractFSBusiness);
    }
}