package ch.supsi.fscli.frontend.controller;

import java.nio.file.Path;

public interface IFSDataLoaderController extends EventHandler {
    void load(Path path);
    void showLoadingView();
}
