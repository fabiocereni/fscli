package ch.supsi.fscli.frontend.i18n;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslationLanguageTest {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    @BeforeEach
    void setup() {}

    @Test
    void testTranslationLanguage() {
        supportedLanguageModel.setSupportedLanguagesTags();
        HashMap<String, HashMap<String, String>> map = supportedLanguageModel.setMapLanguages();
        assertEquals("Modifica", map.get("it_CH").get("label.edit"));
    }



}
