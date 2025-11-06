// PreferencesView.java
package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.PreferencesController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PreferencesView implements IShow {

    private static PreferencesView myself;
    private final PreferencesController preferencesController = PreferencesController.getInstance();

    private PreferencesView() {}

    public static PreferencesView getInstance() {
        if (myself == null) {
            myself = new PreferencesView();
        }
        return myself;
    }

    @Override
    public void showMyView() {
        Stage stage = new Stage();
        stage.setTitle("Preferences");
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(15);
        root.setHgap(10);

        // === LINGUA ===
        Label langLabel = new Label("Language:");
        ComboBox<String> langCombo = new ComboBox<>();
        langCombo.getItems().addAll("en", "it", "de");
        //langCombo.setValue(preferencesController.getCurrentLanguage());

        root.add(langLabel, 0, 0);
        root.add(langCombo, 1, 0);

        // === SPINNER: NUMERO LINEE OUTPUT ===
        Label linesLabel = new Label("Output lines:");
        TextField linesField = new TextField(String.valueOf(preferencesController.getOutputLines()));
        linesField.setPrefColumnCount(4);
        linesField.setEditable(true);

        Button minusBtn = new Button("-");
        Button plusBtn = new Button("+");

        minusBtn.setOnAction(e -> changeValue(linesField, -1));
        plusBtn.setOnAction(e -> changeValue(linesField, +1));

        HBox spinnerBox = new HBox(5, minusBtn, linesField, plusBtn);
        root.add(linesLabel, 0, 1);
        root.add(spinnerBox, 1, 1);

        // === SALVA ===
        Button saveBtn = new Button("Save");
        root.add(saveBtn, 1, 2);
        GridPane.setHalignment(saveBtn, javafx.geometry.HPos.RIGHT);

        saveBtn.setOnAction(e -> {
            try {
                int lines = Integer.parseInt(linesField.getText().trim());
                if (lines < 5 || lines > 100) throw new NumberFormatException();

                //preferencesController.setLanguage(langCombo.getValue());
                preferencesController.setOutputLines(lines);
                preferencesController.savePreferences();

                stage.close();
            } catch (NumberFormatException ex) {
                showError(stage, "Enter a number between 5 and 100.");
            }
        });

        stage.setScene(new Scene(root, 380, 180));
        stage.showAndWait();
    }

    private void changeValue(TextField field, int delta) {
        try {
            int val = Integer.parseInt(field.getText());
            val = Math.max(5, Math.min(100, val + delta));
            field.setText(String.valueOf(val));
        } catch (NumberFormatException e) {
            field.setText("25");
        }
    }

    private void showError(Stage owner, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}