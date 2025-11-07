package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.backend.business.AbstractFSBusiness;
import ch.supsi.fscli.frontend.model.IFSDataSaverModel;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataSaverController implements IFSDataSaverController {

    private final IFSDataSaverModel ifsDataWriterModel;
    private IShow savingView;

    @Inject
    private FSDataSaverController(IFSDataSaverModel ifsDataWriterModel) {
        this.ifsDataWriterModel = ifsDataWriterModel;
    }


    @Override
    public void save(Path path) {
        // TODO bisonga fare il getInstance della classe desiderata
        AbstractFSBusiness abstractFSBusiness = new AbstractFSBusiness() {};
        this.ifsDataWriterModel.save(path, abstractFSBusiness);
    }

    @Override
    public void save() {
        // TODO bisonga fare il getInstance della classe desiderata
        AbstractFSBusiness abstractFSBusiness = new AbstractFSBusiness() {};
        this.ifsDataWriterModel.save(abstractFSBusiness);
    }

    @Override
    public void showSavingView() {
         this.savingView.showMyView();
    }
}