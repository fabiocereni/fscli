package ch.supsi.fscli.frontend.controller.persistence;

import ch.supsi.fscli.frontend.controller.EventHandler;

import java.nio.file.Path;

public interface IFSDataSaverController extends EventHandler {
    void save(Path path);
    void save();
    void showSavingView();
}