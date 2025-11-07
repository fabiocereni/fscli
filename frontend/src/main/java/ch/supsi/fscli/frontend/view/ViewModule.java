package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ViewModule extends AbstractModule {

    protected void configure() {
        bind(IFSDataSaverController.class).to(FSDataSaverController.class).in(Singleton.class);
        bind(IAboutView.class).to(AboutController.class).in(Singleton.class);
        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);
        bind(IFSStateDirector.class).to(FSStateDirector.class).in(Singleton.class);
        bind(CommandLineView.class).in(Singleton.class);
        bind(IQuitView.class).to(QuitView.class).in(Singleton.class);


        bind(IShow.class).annotatedWith(AboutViewQualifier.class).to(AboutView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(HelpViewQualifier.class).to(AboutView.class).in(Singleton.class);


        // TODO da modificare
        bind(QuitController.class).in(Singleton.class);
        // bind(BuildInfoController.class).to(BuildInfoController.class).in(Singleton.class);
        bind(BuildInfoController.class).in(Singleton.class);

    }
}