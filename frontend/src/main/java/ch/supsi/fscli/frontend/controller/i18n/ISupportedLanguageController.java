package ch.supsi.fscli.frontend.controller.i18n;

import java.util.HashMap;
import java.util.List;

public interface ISupportedLanguageController {
    void setSupportedLanguagesTags();
    List<String> getSupportedLanguagesTags();
    void setMapLanguages();
    HashMap<String, String> getMapLanguages();
    void setLanguageTagSelected(String languageTagSelected);
    String getLanguageTagSelected();
    String getTranslation(String key);
}
