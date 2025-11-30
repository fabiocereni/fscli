package ch.supsi.fscli.frontend.controller;

import com.google.inject.ImplementedBy;

import java.io.File;

@ImplementedBy(FSDataReaderController.class)
public interface IFSDataReaderController extends EventHandler {
    void showReaderView();
    void reader(File file);
}
