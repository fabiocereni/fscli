package ch.supsi.fscli.backend.application.i18n;

import ch.supsi.fscli.backend.business.i18n.ISupportedLanguageBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.List;

@Singleton
public class SupportedLanguageApplication implements ISupportedLanguageApplication {

    private final ISupportedLanguageBusiness supportedLanguageBusiness;

    @Inject
    public SupportedLanguageApplication(ISupportedLanguageBusiness supportedLanguageBusiness) {
        this.supportedLanguageBusiness = supportedLanguageBusiness;
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
}
