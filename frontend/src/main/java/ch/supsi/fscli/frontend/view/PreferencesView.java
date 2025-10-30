package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.PreferencesController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PreferencesView {

    public static PreferencesView myself;
    private PreferencesController preferencesController;
    //private TranslationsController translationsController;

    public static PreferencesView getIstance(){
        if (myself == null) {
            myself = new PreferencesView();
        }
        return myself;
    }

    private final Stage stage = new Stage();
    private Slider LineSlider;
    private final ComboBox<String> languageSelector = new ComboBox<>();
    private Button saveButton;

    public void preferencesView() {
        //stage.setTitle(translationsController.translate("preferences.title"));
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(15);
        root.setHgap(10);

        // Label e slider Linee
        //Label lineLabel = new Label(translationsController.translate("preferences.slider"));
        //LineSlider.adjustValue(preferencesController.getCurrentLinesCount());
        LineSlider.setShowTickLabels(true);
        LineSlider.setShowTickMarks(true);
        LineSlider.setMajorTickUnit(10);
        LineSlider.setMinorTickCount(1);
        LineSlider.setBlockIncrement(1);
        LineSlider.setSnapToTicks(true);

        // Label e comboBox lingua
        //Label languageLabel = new Label(translationsController.translate("preferences.box"));
        //languageSelector.getItems().addAll(translationsController.getTagLanguages());
        //languageSelector.setValue(preferencesController.getCurrentLanguage()); // valore predefinito

        // Posizionamento nel GridPane
        //root.add(lineLabel, 0, 0);
        root.add(LineSlider, 1, 0);
        //root.add(languageLabel, 0, 1);
        root.add(languageSelector, 1, 1);
        root.add(saveButton, 1, 2);

        // Listener sul bottone Salva
        saveButton.setOnAction(e -> {
            int bombe = (int) LineSlider.getValue();
            String lingua = languageSelector.getValue();


            preferencesController.updateProperties();
            stage.close();
        });

        stage.setScene(new Scene(root, 350, 200));
    }

    public void show(){
        stage.showAndWait();
    }
}