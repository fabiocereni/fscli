package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.controller.menubar.AboutController;
import ch.supsi.fscli.frontend.controller.menubar.IAboutController;
import ch.supsi.fscli.frontend.controller.persistence.FSDataSaverController;
import ch.supsi.fscli.frontend.controller.persistence.IFSDataSaverController;
import ch.supsi.fscli.frontend.view.*;
import ch.supsi.fscli.frontend.view.menubar.buttonMenubar.*;
import ch.supsi.fscli.frontend.view.menubar.qualifier.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ViewModule extends AbstractModule {

    protected void configure() {
        bind(IFSDataSaverController.class).to(FSDataSaverController.class).in(Singleton.class);
        bind(IAboutController.class).to(AboutController.class).in(Singleton.class);
        bind(CommandLineView.class).in(Singleton.class);

        bind(IShow.class).annotatedWith(AboutViewQualifier.class).to(AboutView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(HelpViewQualifier.class).to(HelpView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(PreferencesViewQualifier.class).to(PreferencesView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(SavingViewQualifier.class).to(SaveAsView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(ReaderViewQualifier.class).to(ReaderView.class).in(Singleton.class);


    }
}