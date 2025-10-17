package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.backend.application.IFSDataWriterApplication;
import ch.supsi.fscli.backend.business.AbstractBusiness;

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
    public void save(Path path, AbstractBusiness abstractBusiness) {
        this.ifsDataWriterApplication.save(path, abstractBusiness);
    }

    @Override
    public void save(AbstractBusiness abstractBusiness) {
        this.ifsDataWriterApplication.save(abstractBusiness);
    }
}