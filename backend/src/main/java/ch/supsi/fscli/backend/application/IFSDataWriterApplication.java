package ch.supsi.fscli.backend.application;

import com.google.inject.ImplementedBy;
import java.nio.file.Path;

@ImplementedBy(FSDataWriterApplication.class)
public interface IFSDataWriterApplication {
    void save(Path path);
    void save();
}
