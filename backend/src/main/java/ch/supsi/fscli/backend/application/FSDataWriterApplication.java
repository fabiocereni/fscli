package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.FSDataWriterBusiness;
import ch.supsi.fscli.backend.business.IFSDataWriterBusiness;

import java.nio.file.Path;

public class FSDataWriterApplication implements IFSDataWriterApplication {

    private final IFSDataWriterBusiness fsDataWriterBusiness = FSDataWriterBusiness.getInstance();

    private static FSDataWriterApplication myself;

    private FSDataWriterApplication() {}

    public static FSDataWriterApplication getInstance() {
        if (myself == null)
            myself = new FSDataWriterApplication();

        return myself;
    }

    @Override
    public void save(Path path) {
        this.fsDataWriterBusiness.save(path);
    }

    @Override
    public void save() {
        this.fsDataWriterBusiness.save();
    }


}