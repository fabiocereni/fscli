package ch.supsi.fscli.backend.business.preference;

import ch.supsi.fscli.backend.DAO.preference.IPreferencesDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreferencesBusinessTest {

    @Mock
    private IPreferencesDAO preferenceDAO;

    @InjectMocks
    private PreferencesBusiness business;

    @Test
    void getProperty_delegatesToDAO() {
        String key = "key";
        String value = "value";
        when(preferenceDAO.getProperty(key)).thenReturn(value);

        String result = business.getProperty(key);

        assertSame(value, result);
        verify(preferenceDAO).getProperty(key);
    }

    @Test
    void setProperty_delegatesToDAO() {
        String key = "key";
        String value = "value";

        business.setProperty(key, value);

        verify(preferenceDAO).setProperty(key, value);
    }

    @Test
    void getProperties_delegatesToDAO() {
        Properties properties = new Properties();
        when(preferenceDAO.getProperties()).thenReturn(properties);

        Properties result = business.getProperties();

        assertSame(properties, result);
        verify(preferenceDAO).getProperties();
    }
}
