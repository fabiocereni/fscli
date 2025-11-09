package ch.supsi.fscli.backend.application.preference;

import java.util.Properties;

public interface IPreferencesApplication {

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();

}
