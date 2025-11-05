package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.PreferencesController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class PreferencesView implements IShow {

    public static PreferencesView myself;
    private final PreferencesController preferencesController = PreferencesController.getInstance();
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

        // numberOfLines
        Label numberLabel = new Label("Numero di linee:");
        TextField numberField = new TextField("10");
        Button minusButton = new Button("-");
        Button plusButton = new Button("+");
        HBox numberBox = new HBox(5, minusButton, numberField, plusButton);

        root.add(numberLabel, 0, 1);
        root.add(numberBox, 1, 1);


        //save
        root.add(saveButton, 1, 2);



        // set on actions
        minusButton.setOnAction(e -> {
            int val = Integer.parseInt(numberField.getText());
            if (val > 1) numberField.setText(String.valueOf(val - 1));
        });

        plusButton.setOnAction(e -> {
            int val = Integer.parseInt(numberField.getText());
            numberField.setText(String.valueOf(val + 1));
        });

        saveButton.setOnAction(e -> {
            // int bombe = (int) LineSlider.getValue();
            String language = languageSelector.getValue();
            int nLines = Integer.parseInt(numberField.getText());
            preferencesController.updateProperties();
            stage.close();
        });

        stage.setScene(new Scene(root, 400, 250));
        stage.showAndWait();
    }

}