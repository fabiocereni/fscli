package ch.supsi.fscli.frontend.i18n;

import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class PreferencesTest {

    private PreferencesModel preferencesModel;

    @BeforeEach
    void setup() {}

    @Test
    void testPreferences() {
        preferencesModel = PreferencesModel.getInstance();
        System.out.println("Modifico il font");
        preferencesModel.setProperty("font-commandLine", "Optima");
        preferencesModel.savePreferences();

        System.out.println("--- Lista di tutte le proprietà ---");

        preferencesModel.setProperty("language-tag", "de_CH");
        preferencesModel.savePreferences();
    }

}
