package ch.supsi.fscli.frontend.controller.i18n;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.List;

public class SupportedLanguageController implements ISupportedLanguageController {

    @Inject
    private ISupportedLanguageModel supportedLanguageModel;

    @Inject
    @Override
    public void setSupportedLanguagesTags() {
        supportedLanguageModel.setSupportedLanguagesTags();
    }

    @Override
    public void setMapLanguages() {
        supportedLanguageModel.setMapLanguages();
    }

    public void setLanguageTagSelected(String languageTagSelected) {
        supportedLanguageModel.setLanguageTagSelected(languageTagSelected);
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageModel.getSupportedLanguagesTags();
    }

    @Override
    public HashMap<String, String> getMapLanguages() {
        return supportedLanguageModel.getMapLanguages();
    }

    @Override
    public String getTranslation(String key) {
        return supportedLanguageModel.getTranslation(key);
    }

    public String getLanguageTagSelected() {
        return supportedLanguageModel.getLanguageTagSelected();
    }

}
