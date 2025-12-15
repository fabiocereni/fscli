package ch.supsi.fscli.frontend.controller.preference;

import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.menubar.qualifier.PreferencesViewQualifier;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Properties;

@Singleton
public class PreferencesController implements IPreferencesController {

    @Inject
    @PreferencesViewQualifier
    private IShow preferencesView;

    @Inject
    private IPreferencesModel preferencesModel;

    @Inject
    private LogDirector logDirector;

    @Override
    public void showPreferencesView() {
        preferencesView.showMyView();
    }

    public void savePreferences() {
        preferencesModel.savePreferences();
        logDirector.logSavePreferences();
    }

    @Override
    public String getProperty(String key) {
        return preferencesModel.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        preferencesModel.setProperty(key, value);
    }

    @Override
    public Properties getProperties() {
        return preferencesModel.getProperties();
    }

}
