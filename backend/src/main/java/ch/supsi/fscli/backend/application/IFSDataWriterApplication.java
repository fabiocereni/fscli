package ch.supsi.fscli.backend.application;

import java.nio.file.Path;

public interface IFSDataWriterApplication {
    void save(Path path);
    void save();
}
