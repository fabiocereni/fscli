package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.backend.application.filesystem.creation.FSCreationApplication;
import ch.supsi.fscli.backend.application.filesystem.creation.IFSCreationApplication;
import ch.supsi.fscli.frontend.model.*;
import ch.supsi.fscli.frontend.model.persistence.FSDataReaderModel;
import ch.supsi.fscli.frontend.model.persistence.FSDataSaverModel;
import ch.supsi.fscli.frontend.model.persistence.IFSDataReaderModel;
import ch.supsi.fscli.frontend.model.persistence.IFSDataSaverModel;
import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ModelModule extends AbstractModule {

    @Override
    protected void configure() {
        // sistemare
        bind(IFSCreationApplication.class).to(FSCreationApplication.class).in(Singleton.class);

        bind(IFSStateModel.class).to(FSStateModel.class).in(Singleton.class);
        bind(IPreferencesModel.class).to(PreferencesModel.class).in(Singleton.class);
        bind(IFSDataSaverModel.class).to(FSDataSaverModel.class).in(Singleton.class);
        bind(IFSDataReaderModel.class).to(FSDataReaderModel.class).in(Singleton.class);
        bind(ISupportedLanguageModel.class).to(SupportedLanguageModel.class).in(Singleton.class);
    }
}