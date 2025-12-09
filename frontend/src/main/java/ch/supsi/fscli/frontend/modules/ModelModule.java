package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.model.filesystem.CommandLineModel;
import ch.supsi.fscli.frontend.model.filesystem.FSStateModel;
import ch.supsi.fscli.frontend.model.filesystem.ICommandLineModel;
import ch.supsi.fscli.frontend.model.filesystem.IFSStateModel;
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
        bind(IFSStateModel.class).to(FSStateModel.class).in(Singleton.class);
        bind(IPreferencesModel.class).to(PreferencesModel.class).in(Singleton.class);
        bind(IFSDataSaverModel.class).to(FSDataSaverModel.class).in(Singleton.class);
        bind(IFSDataReaderModel.class).to(FSDataReaderModel.class).in(Singleton.class);
        bind(ISupportedLanguageModel.class).to(SupportedLanguageModel.class).in(Singleton.class);
        bind(ICommandLineModel.class).to(CommandLineModel.class).in(Singleton.class);
    }
}