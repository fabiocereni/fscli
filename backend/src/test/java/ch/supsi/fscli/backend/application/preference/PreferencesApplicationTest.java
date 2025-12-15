package ch.supsi.fscli.backend.application.preference;

import ch.supsi.fscli.backend.business.preference.IPreferencesBusiness;
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
class PreferencesApplicationTest {

    @Mock
    private IPreferencesBusiness preferenceBusiness;

    @InjectMocks
    private PreferencesApplication application;

    @Test
    void getProperty_delegatesToBusiness() {
        String key = "theme";
        String value = "dark";
        when(preferenceBusiness.getProperty(key)).thenReturn(value);

        String result = application.getProperty(key);

        assertSame(value, result);
        verify(preferenceBusiness).getProperty(key);
    }

    @Test
    void setProperty_delegatesToBusiness() {
        String key = "theme";
        String value = "dark";

        application.setProperty(key, value);

        verify(preferenceBusiness).setProperty(key, value);
    }

    @Test
    void getProperties_delegatesToBusiness() {
        Properties properties = new Properties();
        when(preferenceBusiness.getProperties()).thenReturn(properties);

        Properties result = application.getProperties();

        assertSame(properties, result);
        verify(preferenceBusiness).getProperties();
    }
}
