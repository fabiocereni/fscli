package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.PreferencesController;
import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PreferencesView implements IShow {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static PreferencesView myself;

    // view
    private Stage stage;
    private ComboBox<String> languageComboBox;

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

        stage.setTitle(supportedLanguageModel.getTranslation("label.titlePreferences"));
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(15);
        root.setHgap(10);

        // Label e comboBox lingua
        Label languageLabel = new Label(supportedLanguageModel.getTranslation("label.language"));
        languageComboBox.getItems().addAll(supportedLanguageModel.getSupportedLanguagesTags());
        languageComboBox.setValue(supportedLanguageModel.getSupportedLanguagesTags().get(0));

        Button saveButton = new Button(supportedLanguageModel.getTranslation("label.save"));
        saveButton.setOnAction(e -> savePreferences());

        root.add(languageLabel, 0, 0);
        root.add(languageComboBox, 1, 0);
        root.add(saveButton, 1, 1);

        stage.setScene(new Scene(root, 400, 250));
        stage.showAndWait();
    }

    private void savePreferences() {
        String lingua = languageComboBox.getValue();
        stage.close();
    }

}