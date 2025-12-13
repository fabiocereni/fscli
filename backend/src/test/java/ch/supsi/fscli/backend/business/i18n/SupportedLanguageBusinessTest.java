package ch.supsi.fscli.backend.business.i18n;

import ch.supsi.fscli.backend.DAO.i18n.ISupportedLanguageDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupportedLanguageBusinessTest {

    @Mock
    private ISupportedLanguageDAO supportedLanguageDAO;

    @InjectMocks
    private SupportedLanguageBusiness business;

    @Test
    void setSupportedLanguagesTags_delegatesToDAO() {
        List<String> tags = List.of("en", "it");

        business.setSupportedLanguagesTags(tags);

        verify(supportedLanguageDAO).setSupportedLanguagesTags(tags);
    }

    @Test
    void getSupportedLanguagesTags_delegatesToDAO() {
        List<String> tags = List.of("en", "it");
        when(supportedLanguageDAO.getSupportedLanguagesTags()).thenReturn(tags);

        List<String> result = business.getSupportedLanguagesTags();

        assertSame(tags, result);
        verify(supportedLanguageDAO).getSupportedLanguagesTags();
    }

    @Test
    void setMapLanguages_delegatesToDAO() {
        HashMap<String, HashMap<String, String>> map = new HashMap<>();

        business.setMapLanguages(map);

        verify(supportedLanguageDAO).setMapLanguages(map);
    }

    @Test
    void getMapLanguages_delegatesToDAO() {
        String tag = "en";
        HashMap<String, String> map = new HashMap<>();
        when(supportedLanguageDAO.getMapLanguages(tag)).thenReturn(map);

        HashMap<String, String> result = business.getMapLanguages(tag);

        assertSame(map, result);
        verify(supportedLanguageDAO).getMapLanguages(tag);
    }

    @Test
    void getTranslation_returnsKey() {
        String key = "hello";

        String result = business.getTranslation(key);

        assertEquals(key, result);
    }
}
