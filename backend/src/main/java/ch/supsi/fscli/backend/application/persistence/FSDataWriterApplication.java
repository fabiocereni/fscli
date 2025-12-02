package ch.supsi.fscli.backend.application.persistence;

import ch.supsi.fscli.backend.business.persistence.IFSDataWriterBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataWriterApplication implements IFSDataWriterApplication {

    private final IFSDataWriterBusiness fsDataWriterBusiness;

    @Inject
    public FSDataWriterApplication(IFSDataWriterBusiness fsDataWriterBusiness) {
        this.fsDataWriterBusiness = fsDataWriterBusiness;
    }

    @Override
    public void save(Path path) {
        fsDataWriterBusiness.save(path);
    }

    @Override
    public void save() {
        fsDataWriterBusiness.save();
    }
}
