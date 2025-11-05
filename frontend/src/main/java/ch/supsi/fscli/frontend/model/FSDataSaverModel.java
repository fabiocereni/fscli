package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.application.IFSDataWriterApplication;
import ch.supsi.fscli.backend.business.AbstractFSBusiness;

import java.nio.file.Path;

public class FSDataSaverModel implements IFSDataSaverModel {

    private IFSDataWriterApplication ifsDataWriterApplication;

    private static FSDataSaverModel myself;

    private FSDataSaverModel() {}

    public static FSDataSaverModel getInstance() {
        if(myself == null)
            myself = new FSDataSaverModel();

        return myself;
    }

    @Override
    public void save(Path path, AbstractFSBusiness abstractFSBusiness) {
        this.ifsDataWriterApplication.save(path, abstractFSBusiness);
    }

    @Override
    public void save(AbstractFSBusiness abstractFSBusiness) {
        this.ifsDataWriterApplication.save(abstractFSBusiness);
    }
}