package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.AbstractFSBusiness;

import java.nio.file.Path;

public interface IFSDataWriterApplication {
    void save(Path path, AbstractFSBusiness abstractFSBusiness);
    void save(AbstractFSBusiness abstractFSBusiness);
}
