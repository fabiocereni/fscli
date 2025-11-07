package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.backend.application.FSCreationApplication;
import ch.supsi.fscli.backend.application.FSStateApplication;
import ch.supsi.fscli.backend.application.IFSCreationApplication;
import ch.supsi.fscli.backend.application.IFSStateApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DirectorModule extends AbstractModule {

    protected void configure() {
        bind(IFSStateApplication.class).to(FSStateApplication.class).in(Singleton.class);
        bind(IFSCreationApplication.class).to(FSCreationApplication.class).in(Singleton.class);
    }
}
