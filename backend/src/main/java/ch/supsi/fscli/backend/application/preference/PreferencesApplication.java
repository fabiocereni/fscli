package ch.supsi.fscli.backend.application.preference;

import ch.supsi.fscli.backend.business.preference.IPreferencesBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Properties;

@Singleton
public class PreferencesApplication implements IPreferencesApplication {

    private final IPreferencesBusiness preferenceBusiness;

    @Inject
    public PreferencesApplication(IPreferencesBusiness preferenceBusiness) {
        this.preferenceBusiness = preferenceBusiness;
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
