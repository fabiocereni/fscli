package ch.supsi.fscli.frontend.model.i18n;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public interface ISupportedLanguageModel {
    void setSupportedLanguagesTags();
    List<String> getSupportedLanguagesTags();
    HashMap<String, HashMap<String, String>> setMapLanguages();
}
