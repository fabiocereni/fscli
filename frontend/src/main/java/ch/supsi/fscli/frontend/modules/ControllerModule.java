package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.model.FSDataSaverModel;
import ch.supsi.fscli.frontend.model.FSStateModel;
import ch.supsi.fscli.frontend.model.IFSDataSaverModel;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ControllerModule extends AbstractModule {

    protected void configure() {
        bind(IFSDataSaverModel.class).to(FSDataSaverModel.class).in(Singleton.class);
        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);


        // TODO da sistemare
        bind(IAboutView.class).to(AboutController.class).in(Singleton.class);


        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);
        bind(IFSCreationController.class).to(FSCreationController.class).in(Singleton.class);

        // TODO da modificare
        bind(QuitController.class).in(Singleton.class);
        bind(IQuitController.class).to(QuitController.class).in(Singleton.class);
        // bind(BuildInfoController.class).to(BuildInfoController.class).in(Singleton.class);
        bind(BuildInfoController.class).in(Singleton.class);
    }
}
