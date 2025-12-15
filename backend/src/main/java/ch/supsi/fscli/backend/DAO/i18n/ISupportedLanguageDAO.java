package ch.supsi.fscli.backend.DAO.i18n;

import com.google.inject.ImplementedBy;
import java.util.HashMap;
import java.util.List;

@ImplementedBy(SupportedLanguageDAO.class)
public interface ISupportedLanguageDAO {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
    void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages);
    HashMap<String, String> getMapLanguages(String languageTag);
}
