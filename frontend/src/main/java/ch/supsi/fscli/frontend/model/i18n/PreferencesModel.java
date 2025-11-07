package ch.supsi.fscli.frontend.model.i18n;

public class PreferencesModel implements IPreferencesModel {

    ISupportedLanguageModel supportedLanguageModel;


    @Override
    public void getLanguage() {
        supportedLanguageModel.getSupportedLanguagesTags();
    }

    @Override
    public void getFont() {

    }

    @Override
    public void getNumber() {

    }
}
