package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.controller.preference.PreferencesController;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ControllerModule extends AbstractModule {

    protected void configure() {
        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);
        bind(ISupportedLanguageController.class).to(SupportedLanguageController.class).in(Singleton.class);
        bind(IPreferencesController.class).to(PreferencesController.class).in(Singleton.class);

        bind(ICommandLineController.class).to(CommandLineController.class).in(Singleton.class);

        // TODO da sistemare
        bind(IAboutView.class).to(AboutController.class).in(Singleton.class);


        bind(IHelpController.class).to(HelpController.class).in(Singleton.class);
        bind(IFSCreationController.class).to(FSCreationController.class).in(Singleton.class);

        // TODO da modificare
        bind(QuitController.class).in(Singleton.class);
        bind(IQuitController.class).to(QuitController.class).in(Singleton.class);
        // bind(BuildInfoController.class).to(BuildInfoController.class).in(Singleton.class);
        bind(BuildInfoController.class).in(Singleton.class);
    }
}
