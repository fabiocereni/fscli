package ch.supsi.fscli.frontend.modules;

import ch.supsi.fscli.frontend.view.*;
import ch.supsi.fscli.frontend.view.menubar.buttonMenubar.*;
import ch.supsi.fscli.frontend.view.menubar.qualifier.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ViewModule extends AbstractModule {

    protected void configure() {
        bind(CommandLineView.class).in(Singleton.class);

        bind(IShow.class).annotatedWith(PreferencesViewQualifier.class).to(PreferencesView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(AboutViewQualifier.class).to(AboutView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(HelpViewQualifier.class).to(HelpView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(SavingViewQualifier.class).to(SaveAsView.class).in(Singleton.class);
        bind(IShow.class).annotatedWith(ReaderViewQualifier.class).to(OpenFileView.class).in(Singleton.class);
    }
}