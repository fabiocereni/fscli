package ch.supsi.fscli.backend.DAO.i18n;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SupportedLanguageDAOTest {

    @Test
    void setAndGetSupportedLanguagesTags() {
        SupportedLanguageDAO dao = new SupportedLanguageDAO();
        List<String> tags = List.of("en", "it");

        dao.setSupportedLanguagesTags(tags);

        assertSame(tags, dao.getSupportedLanguagesTags());
    }

    @Test
    void setAndGetMapLanguagesForTag() {
        SupportedLanguageDAO dao = new SupportedLanguageDAO();

        HashMap<String, String> enMap = new HashMap<>();
        enMap.put("hello", "Hello");

        HashMap<String, HashMap<String, String>> mapLanguages = new HashMap<>();
        mapLanguages.put("en", enMap);

        dao.setMapLanguages(mapLanguages);

        HashMap<String, String> result = dao.getMapLanguages("en");

        assertSame(enMap, result);
    }

    @Test
    void getMapLanguagesReturnsCorrectValue() {
        SupportedLanguageDAO dao = new SupportedLanguageDAO();

        HashMap<String, String> itMap = new HashMap<>();
        itMap.put("hello", "Ciao");

        HashMap<String, HashMap<String, String>> mapLanguages = new HashMap<>();
        mapLanguages.put("it", itMap);

        dao.setMapLanguages(mapLanguages);

        assertEquals("Ciao", dao.getMapLanguages("it").get("hello"));
    }
}
