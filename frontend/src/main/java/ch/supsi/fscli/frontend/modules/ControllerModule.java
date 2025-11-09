package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.controller.AboutController;
import ch.supsi.fscli.frontend.controller.HelpController;
import ch.supsi.fscli.frontend.controller.IAboutView;
import ch.supsi.fscli.frontend.controller.IHelpController;
import ch.supsi.fscli.frontend.director.FSStateDirector;
import ch.supsi.fscli.frontend.model.FSDataSaverModel;
import ch.supsi.fscli.frontend.model.IFSDataSaverModel;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ControllerModule extends AbstractModule {

    protected void configure() {
        bind(IFSDataSaverModel.class).to(FSDataSaverModel.class).in(Singleton.class);
        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);
        bind(FSStateDirector.class).in(Singleton.class);

        // TODO da sistemare
        bind(IAboutView.class).to(AboutController.class).in(Singleton.class);
    }
}
