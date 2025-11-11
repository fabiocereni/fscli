package ch.supsi.fscli.frontend.controller.preference;

import ch.supsi.fscli.frontend.controller.EventHandler;

import java.util.Properties;

public interface IPreferencesController extends EventHandler {

    void showPreferencesView();
    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();
    void savePreferences();

}
