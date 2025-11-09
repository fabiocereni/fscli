package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.backend.application.FSCreationApplication;
import ch.supsi.fscli.backend.application.FSStateApplication;
import ch.supsi.fscli.backend.application.IFSCreationApplication;
import ch.supsi.fscli.backend.application.IFSStateApplication;
import ch.supsi.fscli.frontend.director.ConfirmExitDirector;
import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.director.WidgetDirector;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DirectorModule extends AbstractModule {

    protected void configure() {
        bind(IFSStateApplication.class).to(FSStateApplication.class).in(Singleton.class);
        bind(IFSCreationApplication.class).to(FSCreationApplication.class).in(Singleton.class);

        bind(FSCreationDirector.class).in(Singleton.class);
        bind(WidgetDirector.class).in(Singleton.class);
        bind(ConfirmExitDirector.class).in(Singleton.class);
    }
}
