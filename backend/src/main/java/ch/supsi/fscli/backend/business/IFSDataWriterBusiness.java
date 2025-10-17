package ch.supsi.fscli.backend.business;

import java.nio.file.Path;

public interface IFSDataWriterBusiness {
    void save(Path path, AbstractBusiness abstractBusiness);
    void save(AbstractBusiness abstractBusiness);
}
