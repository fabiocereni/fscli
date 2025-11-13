package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.model.IFSDataSaverModel;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.SavingViewQualifier;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverController implements IFSDataSaverController {

    @Inject
    private IFSDataSaverModel ifsDataWriterModel;
    @Inject
    private IFSStateModel ifsStateModel;
    @Inject
    @SavingViewQualifier
    private IShow savingView;




    @Override
    public void save(Path path) {
        this.ifsDataWriterModel.save(path);
        this.ifsStateModel.setCloseable(true);
    }

    @Override
    public void save() {
        this.ifsDataWriterModel.save();
        this.ifsStateModel.setCloseable(true);
    }

    @Override
    public void showSavingView() {
         this.savingView.showMyView();
    }
}