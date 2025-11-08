package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PreferencesView implements IShow {

    private final IPreferencesModel preferencesModel = PreferencesModel.getInstance();
    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static PreferencesView myself;

    // view
    private Stage stage;
    private ComboBox<String> languageComboBox;
    private ComboBox<String> fontCommandLineComboBox;
    private ComboBox<String> fontOutputAreaComboBox;
    private ComboBox<String> fontLogAreaComboBox;

    private PreferencesView() {
        supportedLanguageModel.setSupportedLanguagesTags();
    }

    public static PreferencesView getInstance() {
        if(myself == null)
            myself = new PreferencesView();
        return myself;
    }

    @Override
    public void showMyView() {

        stage = new Stage();
        languageComboBox = new ComboBox<>();
        fontCommandLineComboBox = new ComboBox<>();
        fontOutputAreaComboBox = new ComboBox<>();
        fontLogAreaComboBox = new ComboBox<>();

        stage.setTitle(supportedLanguageModel.getTranslation("label.titlePreferences"));
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(15);
        root.setHgap(10);

        // Label e comboBox lingua
        Label languageLabel = new Label(supportedLanguageModel.getTranslation("label.language"));
        languageComboBox.getItems().addAll(supportedLanguageModel.getSupportedLanguagesTags());
        languageComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_LANGUAGE));

        Label fontCommandLineLabel = new Label("Font command line:");
        fontCommandLineComboBox.getItems().addAll(Font.getFamilies());
        fontCommandLineComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_FONT_COMMANDLINE));

        Label fontOutputAreaLabel = new Label("Font output area:");
        fontOutputAreaComboBox.getItems().addAll(Font.getFamilies());
        fontOutputAreaComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA));

        Label fontLogAreaLabel = new Label("Font log area:");
        fontLogAreaComboBox.getItems().addAll(Font.getFamilies());
        fontLogAreaComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_FONT_LOG_AREA));

        Button saveButton = new Button(supportedLanguageModel.getTranslation("label.save"));
        saveButton.setOnAction(e -> savePreferences());

        root.add(languageLabel, 0, 0);
        root.add(languageComboBox, 1, 0);

        root.add(fontCommandLineLabel, 0, 1);
        root.add(fontCommandLineComboBox, 1, 1);

        root.add(fontOutputAreaLabel, 0, 2);
        root.add(fontOutputAreaComboBox, 1, 2);

        root.add(fontLogAreaLabel, 0, 3);
        root.add(fontLogAreaComboBox, 1, 3);

        root.add(saveButton, 1, 4);

        stage.setScene(new Scene(root, 400, 250));
        stage.showAndWait();
    }

    private void savePreferences() {
        String lingua = languageComboBox.getValue();
        String fontCommandLine = fontCommandLineComboBox.getValue();
        String fontOutputArea = fontOutputAreaComboBox.getValue();
        String fontLogArea = fontLogAreaComboBox.getValue();

        preferencesModel.setProperty(PreferencesModel.KEY_LANGUAGE, lingua);
        preferencesModel.setProperty(PreferencesModel.KEY_FONT_COMMANDLINE, fontCommandLine);
        preferencesModel.setProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA, fontOutputArea);
        preferencesModel.setProperty(PreferencesModel.KEY_FONT_LOG_AREA, fontLogArea);

        preferencesModel.savePreferences();
        stage.close();
    }

}