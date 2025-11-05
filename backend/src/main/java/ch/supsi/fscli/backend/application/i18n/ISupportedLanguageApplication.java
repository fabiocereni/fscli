package ch.supsi.fscli.backend.application.i18n;

import java.util.List;

public interface ISupportedLanguageApplication {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
}
