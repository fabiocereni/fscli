package ch.supsi.fscli.backend.business.i18n;

import ch.supsi.fscli.backend.DAO.i18n.ISupportedLanguageDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.List;

@Singleton
public class SupportedLanguageBusiness implements ISupportedLanguageBusiness {

    private final ISupportedLanguageDAO supportedLanguageDAO;

    @Inject
    public SupportedLanguageBusiness(ISupportedLanguageDAO supportedLanguageDAO) {
        this.supportedLanguageDAO = supportedLanguageDAO;
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
    }   // chiedere
}
