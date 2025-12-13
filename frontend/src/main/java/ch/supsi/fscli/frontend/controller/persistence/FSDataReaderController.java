package ch.supsi.fscli.frontend.controller.persistence;

import ch.supsi.fscli.frontend.director.FSLoadDirector;
import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import ch.supsi.fscli.frontend.model.persistence.IFSDataReaderModel;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.menubar.qualifier.ReaderViewQualifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;

@Singleton
public class FSDataReaderController implements IFSDataReaderController {

    @Inject
    private IFSStateModel stateModel;

    @Inject
    private IFSDataReaderModel fsDataReaderModel;

    @Inject
    private LogDirector logDirector;

    @Inject
    private FSLoadDirector fsLoadDirector;

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
            this.fsLoadDirector.manageFileSystemLoading();
        }
    }
}
