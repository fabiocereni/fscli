package ch.supsi.fscli.backend.application.i18n;

import ch.supsi.fscli.backend.business.i18n.ISupportedLanguageBusiness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupportedLanguageApplicationTest {

    @Mock
    private ISupportedLanguageBusiness supportedLanguageBusiness;

    @InjectMocks
    private SupportedLanguageApplication application;

    @Test
    void setSupportedLanguagesTagsDelegation() {
        List<String> tags = List.of("en", "it");

        application.setSupportedLanguagesTags(tags);

        verify(supportedLanguageBusiness).setSupportedLanguagesTags(tags);
    }

    @Test
    void getSupportedLanguagesTagsDelegation() {
        List<String> tags = List.of("en", "it");
        when(supportedLanguageBusiness.getSupportedLanguagesTags()).thenReturn(tags);

        List<String> result = application.getSupportedLanguagesTags();

        assertEquals(tags, result);
        verify(supportedLanguageBusiness).getSupportedLanguagesTags();
    }

    @Test
    void setMapLanguagesDelegation() {
        HashMap<String, HashMap<String, String>> map = new HashMap<>();

        application.setMapLanguages(map);

        verify(supportedLanguageBusiness).setMapLanguages(map);
    }

    @Test
    void getMapLanguagesDelegation() {
        String tag = "en";
        HashMap<String, String> map = new HashMap<>();
        when(supportedLanguageBusiness.getMapLanguages(tag)).thenReturn(map);

        HashMap<String, String> result = application.getMapLanguages(tag);

        assertEquals(map, result);
        verify(supportedLanguageBusiness).getMapLanguages(tag);
    }

    @Test
    void getTranslation_returnsEmptyString() {
        String result = application.getTranslation("key");

        assertEquals("", result);
    }
}
