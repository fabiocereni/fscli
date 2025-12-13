package ch.supsi.fscli.backend.business.preference;

import ch.supsi.fscli.backend.DAO.preference.IPreferencesDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Properties;

@Singleton
public class PreferencesBusiness implements IPreferencesBusiness {

    @Inject
    private IPreferencesDAO preferenceDAO;


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
