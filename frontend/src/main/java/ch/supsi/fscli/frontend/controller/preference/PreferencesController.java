package ch.supsi.fscli.frontend.controller.preference;

import ch.supsi.fscli.backend.application.preference.IPreferencesApplication;
import ch.supsi.fscli.backend.application.preference.PreferencesApplication;
import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;

import java.util.Properties;

public class PreferencesController implements IPreferencesController {

    private IShow preferencesView;
    private final IPreferencesApplication preferenceApplication;

    private static PreferencesController myself;

    private PreferencesController() {
        preferenceApplication = PreferencesApplication.getInstance();
    }

    public static PreferencesController getInstance() {
        if(myself == null)
            myself = new PreferencesController();
        return myself;
    }

    @Override
    public String getProperty(String key) {
        return preferenceApplication.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        preferenceApplication.setProperty(key, value);
    }

    @Override
    public Properties getProperties() {
        return preferenceApplication.getProperties();
    }

    @Override
    public void showPreferencesView() {
        preferencesView.showMyView();
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        preferencesView = eventHandlerInitializer.preferencesView();
    }

}
