package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.backend.business.AbstractBusiness;

import java.nio.file.Path;

public interface IFSDataSaverController extends EventHandler {
    void save(Path path);
    void save();
    void showSavingView();
}