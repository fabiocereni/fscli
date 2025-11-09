package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.backend.business.AbstractFSBusiness;
import ch.supsi.fscli.frontend.model.IFSDataSaverModel;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverController implements IFSDataSaverController {

    private final IFSDataSaverModel ifsDataWriterModel;
    private final IFSStateModel ifsStateModel;
    private IShow savingView;

    @Inject
    private FSDataSaverController(IFSDataSaverModel ifsDataWriterModel, IFSStateModel ifsStateModel) {
        this.ifsDataWriterModel = ifsDataWriterModel;
        this.ifsStateModel = ifsStateModel;
    }


    @Override
    public void save(Path path) {
        // TODO da cambiare con la classe desiderata
        AbstractFSBusiness abstractFSBusiness = new AbstractFSBusiness() {};
        this.ifsDataWriterModel.save(path, abstractFSBusiness);
        this.ifsStateModel.setCloseable(true);
    }

    @Override
    public void save() {
        // TODO da cambiare con la classe desiderata
        AbstractFSBusiness abstractFSBusiness = new AbstractFSBusiness() {};
        this.ifsDataWriterModel.save(abstractFSBusiness);
        this.ifsStateModel.setCloseable(true);
    }

    @Override
    public void showSavingView() {
         this.savingView.showMyView();
    }
}