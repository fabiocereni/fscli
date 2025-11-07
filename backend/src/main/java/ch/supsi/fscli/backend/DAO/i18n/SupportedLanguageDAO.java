package ch.supsi.fscli.backend.DAO.i18n;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class SupportedLanguageDAO implements ISupportedLanguageDAO {

    private static SupportedLanguageDAO myself;

    private List<String> supportedLanguagesTags;
    private HashMap<String, HashMap<String, String>> mapLanguages;


    private SupportedLanguageDAO() {}

    public static SupportedLanguageDAO getInstance() {
        if(myself == null)
            myself = new SupportedLanguageDAO();
        return myself;
    }

    @Override
    public void setSupportedLanguagesTags(List<String> supportedLanguagesTags) {
        this.supportedLanguagesTags = supportedLanguagesTags;
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return this.supportedLanguagesTags;
    }

    @Override
    public void setMapLanguages(HashMap<String, HashMap<String, String>> mapLanguages) {
        this.mapLanguages = mapLanguages;
    }

    @Override
    public HashMap<String, String> getMapLanguages(String languageTag) {
        return this.mapLanguages.get(languageTag);
    }
}
