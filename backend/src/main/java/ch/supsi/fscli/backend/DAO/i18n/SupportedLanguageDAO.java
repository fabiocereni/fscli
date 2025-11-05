package ch.supsi.fscli.backend.DAO.i18n;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SupportedLanguageDAO implements ISupportedLanguageDAO {

    private static SupportedLanguageDAO myself;

    private List<String> supportedLanguagesTags;

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

}
