package ch.supsi.fscli.backend.application.i18n;

import ch.supsi.fscli.backend.business.i18n.ISupportedLanguageBusiness;
import ch.supsi.fscli.backend.business.i18n.SupportedLanguageBusiness;

import java.util.HashMap;
import java.util.List;

public class SupportedLanguageApplication implements ISupportedLanguageApplication {

    private final ISupportedLanguageBusiness supportedLanguageBusiness;

    private static SupportedLanguageApplication myself;

    private SupportedLanguageApplication() {
        supportedLanguageBusiness = SupportedLanguageBusiness.getInstance();
    }

    public static SupportedLanguageApplication getInstance() {
        if(myself == null)
            myself = new SupportedLanguageApplication();
        return myself;
    }

    @Override
    public void setSupportedLanguagesTags(List<String> supportedLanguagesTags) {
        supportedLanguageBusiness.setSupportedLanguagesTags(supportedLanguagesTags);
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageBusiness.getSupportedLanguagesTags();
    }

    @Override
    public void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages) {
        supportedLanguageBusiness.setMapLanguages(mapLanguages);
    }

    @Override
    public HashMap<String, String> getMapLanguages(String languageTag) {
        return supportedLanguageBusiness.getMapLanguages(languageTag);
    }

    public String getTranslation(String key) {
        return "";
    }

}
