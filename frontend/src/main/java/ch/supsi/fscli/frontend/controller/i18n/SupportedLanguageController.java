package ch.supsi.fscli.frontend.controller.i18n;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.List;

public class SupportedLanguageController implements ISupportedLanguageController {

    @Inject
    private ISupportedLanguageModel supportedLanguageModel;

    @Inject
    private PreferencesModel preferencesModel;

    @Inject
    @Override
    public void setSupportedLanguagesTags() {
        supportedLanguageModel.setSupportedLanguagesTags();
        supportedLanguageModel.setMapLanguages();
        setLanguageTagSelected(preferencesModel.getProperty(PreferencesModel.KEY_LANGUAGE));
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageModel.getSupportedLanguagesTags();
    }

    @Override
    public void setMapLanguages() {
        supportedLanguageModel.setMapLanguages();
    }

    @Override
    public HashMap<String, String> getMapLanguages() {
        return supportedLanguageModel.getMapLanguages();
    }

    public void setLanguageTagSelected(String languageTagSelected) {
        supportedLanguageModel.setLanguageTagSelected(languageTagSelected);
    }

    public String getLanguageTagSelected() {
        return supportedLanguageModel.getLanguageTagSelected();
    }

    @Override
    public String getTranslation(String key) {
        return supportedLanguageModel.getTranslation(key);
    }
}
