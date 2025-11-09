package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.model.FSStateModel;
import ch.supsi.fscli.frontend.model.IFSStateModel;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ModelModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IFSStateModel.class).to(FSStateModel.class).in(Singleton.class);
    }
}
