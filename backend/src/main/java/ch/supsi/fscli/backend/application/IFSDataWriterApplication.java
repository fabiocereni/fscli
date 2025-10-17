package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.AbstractBusiness;

import java.nio.file.Path;

public interface IFSDataWriterApplication {
    void save(Path path, AbstractBusiness abstractBusiness);
    void save(AbstractBusiness abstractBusiness);
}
