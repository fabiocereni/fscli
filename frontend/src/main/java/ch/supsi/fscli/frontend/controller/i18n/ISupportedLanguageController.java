package ch.supsi.fscli.frontend.controller.i18n;

import java.util.List;

public interface ISupportedLanguageController {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
}
