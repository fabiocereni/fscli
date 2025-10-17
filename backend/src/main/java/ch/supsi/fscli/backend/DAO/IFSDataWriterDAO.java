package ch.supsi.fscli.backend.DAO;

import java.nio.file.Path;

public interface IFSDataWriterDAO {
    void save(Path path, String toPersist);
}