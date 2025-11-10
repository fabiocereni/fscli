package ch.supsi.fscli.frontend.controller.preference;

import ch.supsi.fscli.backend.application.preference.IPreferencesApplication;
import ch.supsi.fscli.backend.application.preference.PreferencesApplication;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;

import java.util.Properties;

public class PreferencesController implements IPreferencesController {

    private IShow preferencesView;
    private final IPreferencesModel preferencesModel = PreferencesModel.getInstance();

    private static PreferencesController myself;

    private PreferencesController() {
    }

    public static PreferencesController getInstance() {
        if(myself == null)
            myself = new PreferencesController();
        return myself;
    }

    @Override
    public void showPreferencesView() {
        preferencesView.showMyView();
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        preferencesView = eventHandlerInitializer.preferencesView();
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
