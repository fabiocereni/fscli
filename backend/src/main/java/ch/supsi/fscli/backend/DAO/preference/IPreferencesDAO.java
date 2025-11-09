package ch.supsi.fscli.backend.DAO.preference;

import java.util.Properties;

public interface IPreferencesDAO {

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();

}
