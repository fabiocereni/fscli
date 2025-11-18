package ch.supsi.fscli.backend.business.i18n;

import ch.supsi.fscli.backend.DAO.i18n.ISupportedLanguageDAO;
import ch.supsi.fscli.backend.DAO.i18n.SupportedLanguageDAO;

import java.util.HashMap;
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

    @Override
    public void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages) {
        supportedLanguageDAO.setMapLanguages(mapLanguages);
    }

    @Override
    public HashMap<String, String> getMapLanguages(String languageTag) {
        return supportedLanguageDAO.getMapLanguages(languageTag);
    }

    @Override
    public String getTranslation(String key) {
        return key;
    }
}
