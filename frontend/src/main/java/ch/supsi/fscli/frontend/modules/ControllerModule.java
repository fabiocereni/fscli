package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.controller.filesystem.CommandLineController;
import ch.supsi.fscli.frontend.controller.filesystem.creation.FSCreationController;
import ch.supsi.fscli.frontend.controller.filesystem.ICommandLineController;
import ch.supsi.fscli.frontend.controller.filesystem.creation.IFSCreationController;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;
import ch.supsi.fscli.frontend.controller.menubar.*;
import ch.supsi.fscli.frontend.controller.menubar.IAboutController;
import ch.supsi.fscli.frontend.controller.persistence.FSDataReaderController;
import ch.supsi.fscli.frontend.controller.persistence.FSDataSaverController;
import ch.supsi.fscli.frontend.controller.persistence.IFSDataReaderController;
import ch.supsi.fscli.frontend.controller.persistence.IFSDataSaverController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.controller.preference.PreferencesController;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ControllerModule extends AbstractModule {
    protected void configure() {
        bind(ISupportedLanguageController.class).to(SupportedLanguageController.class).in(Singleton.class);
        bind(IPreferencesController.class).to(PreferencesController.class).in(Singleton.class);

        bind(IFSDataReaderController.class).to(FSDataReaderController.class).in(Singleton.class);
        bind(IFSDataSaverController.class).to(FSDataSaverController.class).in(Singleton.class);

        bind(IFSCreationController.class).to(FSCreationController.class).in(Singleton.class);
        bind(ICommandLineController.class).to(CommandLineController.class).in(Singleton.class);
        bind(BuildInfoController.class).in(Singleton.class);

        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);
        bind(IQuitController.class).to(QuitController.class).in(Singleton.class);
        bind(IAboutController.class).to(AboutController.class).in(Singleton.class);
    }
}
