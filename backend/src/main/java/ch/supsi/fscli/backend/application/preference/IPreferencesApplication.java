package ch.supsi.fscli.backend.application.preference;

import com.google.inject.ImplementedBy;
import java.util.Properties;

@ImplementedBy(PreferencesApplication.class)
public interface IPreferencesApplication {

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();
}
