package ch.supsi.fscli.backend.business.preference;

import com.google.inject.ImplementedBy;
import java.util.Properties;

@ImplementedBy(PreferencesBusiness.class)
public interface IPreferencesBusiness {

    String getProperty(String key);
    void setProperty(String key, String value);
    Properties getProperties();

}
