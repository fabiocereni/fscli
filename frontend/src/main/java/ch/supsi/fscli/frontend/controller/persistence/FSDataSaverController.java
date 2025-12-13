package ch.supsi.fscli.frontend.controller.persistence;

import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.director.SaveEventDirector;
import ch.supsi.fscli.frontend.model.persistence.IFSDataSaverModel;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.menubar.qualifier.SavingViewQualifier;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverController implements IFSDataSaverController {

    @Inject
    private IFSDataSaverModel ifsDataWriterModel;

    @Inject
    private SaveEventDirector saveEventDirector;

    @Inject
    private LogDirector logDirector;

    @Inject
    private IFSStateModel ifsStateModel;

    @Inject
    @SavingViewQualifier
    private IShow savingView;

    @Override
    public void save(Path path) {
        this.ifsStateModel.setCloseable(true);
        this.ifsDataWriterModel.save(path);
        saveEventDirector.manageSaveAs();
        logDirector.logSaveFS();
    }

    @Override
    public void save() {
        this.ifsStateModel.setCloseable(true);
        this.ifsDataWriterModel.save();
        saveEventDirector.manageSave();
        logDirector.logSaveFS();
    }

    @Override
    public void showSavingView() {
         this.savingView.showMyView();
    }
}