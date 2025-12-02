package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.model.IFSDataReaderModel;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.ReaderViewQualifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;

@Singleton
public class FSDataReaderController implements IFSDataReaderController {

    @Inject
    private IFSDataReaderModel fsDataReaderModel;

    @Inject
    private LogDirector logDirector;

    @Inject
    @ReaderViewQualifier
    private IShow readerView;

    @Override
    public void showReaderView() {
        this.readerView.showMyView();
    }

    @Override
    public void reader(File file) {
        if (file != null) {
            this.fsDataReaderModel.reader(file);
            logDirector.logLoadFS();
        }
    }
}
