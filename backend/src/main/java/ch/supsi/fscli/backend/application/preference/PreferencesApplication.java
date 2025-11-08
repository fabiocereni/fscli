package ch.supsi.fscli.backend.application.preference;

import ch.supsi.fscli.backend.business.preference.IPreferencesBusiness;
import ch.supsi.fscli.backend.business.preference.PreferencesBusiness;

import java.util.Properties;

public class PreferencesApplication implements IPreferencesApplication {

    private final IPreferencesBusiness preferenceBusiness;

    private static PreferencesApplication myself;

    private PreferencesApplication() {
        preferenceBusiness = PreferencesBusiness.getInstance();
    }

    public static PreferencesApplication getInstance() {
        if(myself == null)
            myself = new PreferencesApplication();
        return myself;
    }

    @Override
    public String getProperty(String key) {
        return preferenceBusiness.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        preferenceBusiness.setProperty(key, value);
    }

    @Override
    public Properties getProperties() {
        return preferenceBusiness.getProperties();
    }
}
