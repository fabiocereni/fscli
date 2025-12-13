package ch.supsi.fscli.backend.DAO.preference;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PreferencesDAOTest {


    private final IPreferencesDAO preferenceDAO = new PreferencesDAO();

    @Test
    void setAndGetProperty() {

        preferenceDAO.setProperty("key", "value");

        assertEquals("value", preferenceDAO.getProperty("key"));
    }

    @Test
    void getProperties_returnsInternalProperties() {

        Properties properties = preferenceDAO.getProperties();

        assertEquals(properties, preferenceDAO.getProperties());
    }

    @Test
    void getProperty_returnsNullIfNotPresent() {

        String result = preferenceDAO.getProperty("null");

        assertNull(result);
    }

}
