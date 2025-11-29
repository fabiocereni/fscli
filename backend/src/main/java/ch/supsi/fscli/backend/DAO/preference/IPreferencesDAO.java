package ch.supsi.fscli.backend.DAO.preference;

import com.google.inject.ImplementedBy;
import java.util.Properties;

@ImplementedBy(PreferencesDAO.class)
public interface IPreferencesDAO {

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();
}
