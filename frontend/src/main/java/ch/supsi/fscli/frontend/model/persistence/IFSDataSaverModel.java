package ch.supsi.fscli.frontend.model.persistence;

import java.nio.file.Path;

public interface IFSDataSaverModel {
    void save(Path path);
    void save();
    String getPathToPrint();
}