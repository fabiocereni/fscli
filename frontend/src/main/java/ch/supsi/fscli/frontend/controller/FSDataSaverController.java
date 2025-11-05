package ch.supsi.fscli.frontend.controller;


import ch.supsi.fscli.backend.business.AbstractFSBusiness;
import ch.supsi.fscli.frontend.model.FSDataSaverModel;
import ch.supsi.fscli.frontend.model.IFSDataSaverModel;
import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;

import java.nio.file.Path;

public class FSDataSaverController implements IFSDataSaverController {

    private final IFSDataSaverModel ifsDataWriterModel = FSDataSaverModel.getInstance();
    private IShow savingView;

    private static FSDataSaverController myself;

    private FSDataSaverController() {}

    public static FSDataSaverController getInstance() {
        if(myself == null)
            myself = new FSDataSaverController();

        return myself;
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        this.savingView = eventHandlerInitializer.savingView();
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