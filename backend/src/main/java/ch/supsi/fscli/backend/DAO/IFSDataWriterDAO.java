package ch.supsi.fscli.backend.DAO;

import com.google.inject.ImplementedBy;
import java.nio.file.Path;

@ImplementedBy(FSDataWriterDAO.class)
public interface IFSDataWriterDAO {
    void save(Path path, String toPersist);
}
