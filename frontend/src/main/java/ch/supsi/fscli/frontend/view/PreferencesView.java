package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.PreferencesController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class PreferencesView implements IShow {

    public static PreferencesView myself;
    private PreferencesController preferencesController;
    //private TranslationsController translationsController;

    private PreferencesView() {
    }

    public static PreferencesView getInstance() {
        if (myself == null) {
            myself = new PreferencesView();
        }
        return myself;
    }

    @Override
    public void showMyView() {
        Stage stage = new Stage();
        Slider LineSlider;
        ComboBox<String> languageSelector = new ComboBox<>();
        Button saveButton;

        stage.setTitle("preferences.title");
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(15);
        root.setHgap(10);

        saveButton = new Button("Salva");

        // Label e comboBox lingua
        Label languageLabel = new Label("translationsController.translate(preferences.box)");
        //languageSelector.getItems().addAll(translationsController.getTagLanguages());
        languageSelector.getItems().addAll(List.of("en", "it", "de"));
        //languageSelector.setValue(preferencesController.getCurrentLanguage());
        languageSelector.setValue("en");

        root.add(languageLabel, 0, 0);
        root.add(languageSelector, 1, 0);
        root.add(saveButton, 1, 1);

        saveButton.setOnAction(e -> {
            // int bombe = (int) LineSlider.getValue();
            String lingua = languageSelector.getValue();

            //preferencesController.updateProperties();
            stage.close();
        });

        stage.setScene(new Scene(root, 400, 250));
        stage.showAndWait();
    }

}