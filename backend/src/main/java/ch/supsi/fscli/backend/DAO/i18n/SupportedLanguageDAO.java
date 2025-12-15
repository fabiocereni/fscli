package ch.supsi.fscli.backend.DAO.i18n;

import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.List;

@Singleton
public class SupportedLanguageDAO implements ISupportedLanguageDAO {

    private List<String> supportedLanguagesTags;
    private HashMap<String, HashMap<String, String>> mapLanguages;

    public SupportedLanguageDAO() {}

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
