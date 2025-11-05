package ch.supsi.fscli.backend.business.i18n;

import java.util.List;

public interface ISupportedLanguageBusiness {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
}
