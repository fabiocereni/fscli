package ch.supsi.fscli.backend.business.i18n;

import ch.supsi.fscli.backend.DAO.i18n.ISupportedLanguageDAO;
import ch.supsi.fscli.backend.DAO.i18n.SupportedLanguageDAO;

import java.util.List;

public class SupportedLanguageBusiness implements ISupportedLanguageBusiness {

    private final ISupportedLanguageDAO supportedLanguageDAO;

    private static SupportedLanguageBusiness myself;

    private SupportedLanguageBusiness() {
        supportedLanguageDAO = SupportedLanguageDAO.getInstance();
    }

    public static SupportedLanguageBusiness getInstance() {
        if(myself == null)
            myself = new SupportedLanguageBusiness();
        return myself;
    }

    @Override
    public void setSupportedLanguagesTags(List<String> supportedLanguagesTags) {
        supportedLanguageDAO.setSupportedLanguagesTags(supportedLanguagesTags);
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageDAO.getSupportedLanguagesTags();
    }
}
