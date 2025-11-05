package ch.supsi.fscli.backend.DAO.i18n;

import java.util.List;

public interface ISupportedLanguageDAO {
    void setSupportedLanguagesTags(List<String> supportedLanguagesTags);
    List<String> getSupportedLanguagesTags();
}
