package ch.supsi.fscli.backend.DAO.i18n;

import java.util.HashMap;
import java.util.List;

public interface ISupportedLanguageDAO {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
    void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages);
    HashMap<String, String> getMapLanguages(String languageTag);
}
