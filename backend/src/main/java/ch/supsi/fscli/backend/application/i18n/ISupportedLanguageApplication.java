package ch.supsi.fscli.backend.application.i18n;

import com.google.inject.ImplementedBy;
import java.util.HashMap;
import java.util.List;

@ImplementedBy(SupportedLanguageApplication.class)
public interface ISupportedLanguageApplication {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
    void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages);
    HashMap<String, String> getMapLanguages(String languageTag);
}
