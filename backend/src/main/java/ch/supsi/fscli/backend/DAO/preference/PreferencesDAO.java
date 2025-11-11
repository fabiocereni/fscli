package ch.supsi.fscli.backend.DAO.preference;

import java.util.Properties;

public class PreferencesDAO implements IPreferencesDAO {

    private static PreferencesDAO myself;

    private Properties properties;

    private PreferencesDAO() {
        properties = new Properties();
    }

    public static PreferencesDAO getInstance() {
        if(myself == null)
            myself = new PreferencesDAO();
        return myself;
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
