package ch.supsi.fscli.backend.business.preference;

import ch.supsi.fscli.backend.DAO.preference.IPreferencesDAO;
import ch.supsi.fscli.backend.DAO.preference.PreferencesDAO;

import java.util.Properties;

public class PreferencesBusiness implements IPreferencesBusiness {

    private final IPreferencesDAO preferenceDAO;

    private static PreferencesBusiness myself;

    private PreferencesBusiness() {
        preferenceDAO = PreferencesDAO.getInstance();
    }

    public static PreferencesBusiness getInstance() {
        if(myself == null)
            myself = new PreferencesBusiness();
        return myself;
    }

    @Override
    public String getProperty(String key) {
        return preferenceDAO.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        preferenceDAO.setProperty(key, value);
    }

    @Override
    public Properties getProperties() {
        return preferenceDAO.getProperties();
    }
}
