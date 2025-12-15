package ch.supsi.fscli.frontend.controller.persistence;

import ch.supsi.fscli.frontend.controller.EventHandler;
import com.google.inject.ImplementedBy;

import java.io.File;

public interface IFSDataReaderController extends EventHandler {
    void reader(File file);
    void showReaderView();
}
