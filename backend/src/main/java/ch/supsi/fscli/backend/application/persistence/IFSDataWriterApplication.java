package ch.supsi.fscli.backend.application.persistence;

import com.google.inject.ImplementedBy;
import java.nio.file.Path;

@ImplementedBy(FSDataWriterApplication.class)
public interface IFSDataWriterApplication {
    void save(Path path);
    void save();
}
