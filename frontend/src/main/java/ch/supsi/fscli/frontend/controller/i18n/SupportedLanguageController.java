package ch.supsi.fscli.frontend.controller.i18n;

import ch.supsi.fscli.backend.application.i18n.ISupportedLanguageApplication;
import ch.supsi.fscli.backend.application.i18n.SupportedLanguageApplication;

import java.util.HashMap;
import java.util.List;

public class SupportedLanguageController implements ISupportedLanguageController {

    private final ISupportedLanguageApplication supportedLanguageApplication;

    private static SupportedLanguageController myself;

    private SupportedLanguageController() {
        supportedLanguageApplication = SupportedLanguageApplication.getInstance();
    }

    public static SupportedLanguageController getInstance() {
        if(myself == null)
            myself = new SupportedLanguageController();
        return myself;
    }

    @Override
    public void setSupportedLanguagesTags(List<String> supportedLanguagesTags) {
        supportedLanguageApplication.setSupportedLanguagesTags(supportedLanguagesTags);
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageApplication.getSupportedLanguagesTags();
    }

    @Override
    public void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages) {
        supportedLanguageApplication.setMapLanguages(mapLanguages);
    }

    @Override
    public HashMap<String, String> getMapLanguages(String languageTag) {
        return supportedLanguageApplication.getMapLanguages(languageTag);
    }
}
