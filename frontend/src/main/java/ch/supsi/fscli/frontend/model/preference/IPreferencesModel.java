package ch.supsi.fscli.frontend.model.preference;

import java.util.Properties;

public interface IPreferencesModel {

    void savePreferences();

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();
}
