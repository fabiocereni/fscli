package ch.supsi.fscli.backend.DAO.preference;

import com.google.inject.Singleton;
import java.util.Properties;

@Singleton
public class PreferencesDAO implements IPreferencesDAO {

    private final Properties properties;

    public PreferencesDAO() {
        this.properties = new Properties();
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
