package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.backend.application.filesystem.creation.FSCreationApplication;
import ch.supsi.fscli.backend.application.filesystem.state.FSStateApplication;
import ch.supsi.fscli.backend.application.filesystem.creation.IFSCreationApplication;
import ch.supsi.fscli.backend.application.filesystem.state.IFSStateApplication;
import ch.supsi.fscli.frontend.director.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DirectorModule extends AbstractModule {

    protected void configure() {
        bind(IFSStateApplication.class).to(FSStateApplication.class).in(Singleton.class);
        bind(IFSCreationApplication.class).to(FSCreationApplication.class).in(Singleton.class);

        bind(FSCreationDirector.class).in(Singleton.class);
        bind(WidgetDirector.class).in(Singleton.class);
        bind(ConfirmExitDirector.class).in(Singleton.class);
        bind(SaveEventDirector.class).in(Singleton.class);
        bind(LogDirector.class).in(Singleton.class);
    }
}
