package ch.supsi.fscli.frontend.i18n;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupportedLanguageTest {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    @BeforeEach
    void setup() {}

    @Test
    void testSupportedLanguageModel() {
        List<String> tagLanguages = List.of("en_US", "it_CH", "de_CH");
        supportedLanguageModel.setSupportedLanguagesTags();
        assertEquals(tagLanguages, supportedLanguageModel.getSupportedLanguagesTags());
    }

}
