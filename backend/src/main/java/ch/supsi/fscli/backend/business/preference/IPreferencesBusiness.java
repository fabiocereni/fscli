package ch.supsi.fscli.backend.business.preference;

import java.util.Properties;

public interface IPreferencesBusiness {

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();

}
