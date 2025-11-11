//package ch.supsi.fscli.frontend.i18n;
//
//import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
//import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TranslationLanguageTest {
//
//    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();
//
//    @BeforeEach
//    void setup() {}
//
//    @Test
//    void testTranslationLanguage() {
//        supportedLanguageModel.setSupportedLanguagesTags();
//        supportedLanguageModel.setMapLanguages();
//        supportedLanguageModel.setLanguageTagSelected("de_CH");
//        HashMap<String, String> map = supportedLanguageModel.getMapLanguages();
//        assertEquals("Bearbeiten", map.get("label.edit"));
//    }
//
//
//
//}
