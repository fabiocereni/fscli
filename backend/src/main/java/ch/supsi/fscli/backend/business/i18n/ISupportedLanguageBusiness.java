package ch.supsi.fscli.backend.business.i18n;

import com.google.inject.ImplementedBy;
import java.util.HashMap;
import java.util.List;

@ImplementedBy(SupportedLanguageBusiness.class)
public interface ISupportedLanguageBusiness {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
    void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages);
    HashMap<String, String> getMapLanguages(String languageTag);
}
