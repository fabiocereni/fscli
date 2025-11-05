package ch.supsi.fscli.backend.application.i18n;

import ch.supsi.fscli.backend.DAO.i18n.ISupportedLanguageDAO;
import ch.supsi.fscli.backend.business.i18n.ISupportedLanguageBusiness;
import ch.supsi.fscli.backend.business.i18n.SupportedLanguageBusiness;

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
}
