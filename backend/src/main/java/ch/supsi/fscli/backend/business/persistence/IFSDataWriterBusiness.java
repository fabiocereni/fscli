package ch.supsi.fscli.backend.business.persistence;

import com.google.inject.ImplementedBy;
import java.nio.file.Path;

@ImplementedBy(FSDataWriterBusiness.class)
public interface IFSDataWriterBusiness {
    void save(Path path);
    void save();
}
