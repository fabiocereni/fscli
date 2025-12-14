package ch.supsi.fscli.frontend.view.menubar.buttonMenubar;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import jakarta.inject.Singleton;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

@Singleton
public class PreferencesView implements IShow {
    @Inject
    private IPreferencesModel preferencesModel;

    @Inject
    private IPreferencesController preferencesController;

    @Inject
    private ISupportedLanguageController supportedLanguageController;

    // view
    private Stage stage;
    private ComboBox<String> languageComboBox;
    private ComboBox<String> fontCommandLineComboBox;
    private ComboBox<String> fontOutputAreaComboBox;
    private ComboBox<String> fontLogAreaComboBox;
    private TextField linesField;
    private TextField linesField2;
    private TextField columnsField;
    private Button saveButton;

    // Stato iniziale per i confronti
    private String initLanguage;
    private String initFontCmd;
    private String initFontOut;
    private String initFontLog;
    private String initLines;
    private String initLines2;
    private String initColumns;

    @Override
    public void showMyView() {
        stage = new Stage();

        languageComboBox = new ComboBox<>();
        languageComboBox.setId("languageComboBox");
        fontCommandLineComboBox = new ComboBox<>();
        fontOutputAreaComboBox = new ComboBox<>();
        fontLogAreaComboBox = new ComboBox<>();

        stage.setTitle(supportedLanguageController.getTranslation("label.titlePreferences"));
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setResizable(false);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(8);
        root.setHgap(10);

        Label languageLabel = new Label(supportedLanguageController.getTranslation("label.language"));
        languageComboBox.getItems().addAll(supportedLanguageController.getSupportedLanguagesTags());
        languageComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_LANGUAGE));
        initLanguage = preferencesModel.getProperty(PreferencesModel.KEY_LANGUAGE);

        Label fontCommandLineLabel = new Label("Font command line:");
        fontCommandLineComboBox.getItems().addAll(Font.getFamilies());
        fontCommandLineComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_FONT_COMMANDLINE));
        initFontCmd = preferencesModel.getProperty(PreferencesModel.KEY_FONT_COMMANDLINE);

        Label fontOutputAreaLabel = new Label("Font output area:");
        fontOutputAreaComboBox.getItems().addAll(Font.getFamilies());
        fontOutputAreaComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA));
        initFontOut = preferencesModel.getProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA);

        Label fontLogAreaLabel = new Label("Font log area:");
        fontLogAreaComboBox.getItems().addAll(Font.getFamilies());
        fontLogAreaComboBox.setValue(preferencesModel.getProperty(PreferencesModel.KEY_FONT_LOG_AREA));
        initFontLog = preferencesModel.getProperty(PreferencesModel.KEY_FONT_LOG_AREA);

        Label linesLabel = new Label(supportedLanguageController.getTranslation("label.line"));
        linesField = new TextField(preferencesModel.getProperty(PreferencesModel.KEY_LINES_NUMBER));
        linesField.setId("linesField");
        linesField.setPrefColumnCount(4);
        linesField.setEditable(true);
        initLines = preferencesModel.getProperty(PreferencesModel.KEY_LINES_NUMBER);

        Label linesLabel2 = new Label(supportedLanguageController.getTranslation("label.line2"));
        linesField2 = new TextField(preferencesModel.getProperty(PreferencesModel.KEY_LINES_NUMBER_LOG));
        linesField2.setId("linesField2");
        linesField2.setPrefColumnCount(4);
        linesField2.setEditable(true);
        initLines2 = preferencesModel.getProperty(PreferencesModel.KEY_LINES_NUMBER_LOG);

        Label columns = new Label(supportedLanguageController.getTranslation("label.column"));
        columnsField = new TextField(preferencesModel.getProperty(PreferencesModel.KEY_COLUMNS_NUMBER));
        columnsField.setId("columnsField");
        columnsField.setPrefColumnCount(4);
        columnsField.setEditable(true);
        initColumns = preferencesModel.getProperty(PreferencesModel.KEY_COLUMNS_NUMBER);

        ChangeListener<Object> commonListener = (obs, oldVal, newVal) -> checkChanges();

        languageComboBox.valueProperty().addListener(commonListener);
        fontCommandLineComboBox.valueProperty().addListener(commonListener);
        fontOutputAreaComboBox.valueProperty().addListener(commonListener);
        fontLogAreaComboBox.valueProperty().addListener(commonListener);
        linesField.textProperty().addListener(commonListener);
        linesField2.textProperty().addListener(commonListener);
        columnsField.textProperty().addListener(commonListener);

        root.add(languageLabel, 0, 0);
        root.add(languageComboBox, 1, 0);
        root.add(fontCommandLineLabel, 0, 1);
        root.add(fontCommandLineComboBox, 1, 1);
        root.add(fontOutputAreaLabel, 0, 2);
        root.add(fontOutputAreaComboBox, 1, 2);
        root.add(fontLogAreaLabel, 0, 3);
        root.add(fontLogAreaComboBox, 1, 3);

        Button minusBtn = new Button("-");
        Button plusBtn = new Button("+");
        minusBtn.setOnAction(e -> changeValue(linesField, -1));
        plusBtn.setOnAction(e -> changeValue(linesField, +1));

        HBox spinnerBox = new HBox(5, minusBtn, linesField, plusBtn);
        root.add(linesLabel, 0, 4);
        root.add(spinnerBox, 1, 4);

        Button minusBtn2 = new Button("-");
        Button plusBtn2 = new Button("+");
        minusBtn2.setOnAction(e -> changeValue(linesField2, -1));
        plusBtn2.setOnAction(e -> changeValue(linesField2, +1));

        HBox spinnerBox2 = new HBox(5, minusBtn2, linesField2, plusBtn2);
        root.add(linesLabel2, 0, 5);
        root.add(spinnerBox2, 1, 5);

        Button minusBtn3 = new Button("-");
        Button plusBtn3 = new Button("+");
        minusBtn3.setOnAction(e -> changeValue(columnsField, -1));
        plusBtn3.setOnAction(e -> changeValue(columnsField, +1));

        HBox spinnerBox3 = new HBox(5, minusBtn3, columnsField, plusBtn3);
        root.add(columns, 0, 6);
        root.add(spinnerBox3, 1, 6);

        saveButton = new Button(supportedLanguageController.getTranslation("label.save"));
        root.add(saveButton, 1, 7);
        saveButton.setDisable(true);

        saveButton.setOnAction(e -> {
            try {
                int lines = Integer.parseInt(linesField.getText().trim());
                int lines2 = Integer.parseInt(linesField2.getText().trim());

                if ((lines < 3 || lines > 100) || (lines2 < 3 || lines2 > 100)) throw new NumberFormatException();
                savePreferences();
            } catch (NumberFormatException ex) {
                showError(stage, "Enter a number between 3 and 100.");
            }

        });
        saveButton.setId("saveButton");
        stage.setScene(new Scene(root, 450, 300));
        stage.showAndWait();
    }

    private void savePreferences() {
        String lingua = languageComboBox.getValue();
        String fontCommandLine = fontCommandLineComboBox.getValue();
        String fontOutputArea = fontOutputAreaComboBox.getValue();
        String fontLogArea = fontLogAreaComboBox.getValue();
        int nLines = Integer.parseInt(linesField.getText().trim());
        int nLines2 = Integer.parseInt(linesField2.getText().trim());
        int nColumns = Integer.parseInt(columnsField.getText().trim());

        preferencesController.setProperty(PreferencesModel.KEY_LANGUAGE, lingua);
        preferencesController.setProperty(PreferencesModel.KEY_FONT_COMMANDLINE, fontCommandLine);
        preferencesController.setProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA, fontOutputArea);
        preferencesController.setProperty(PreferencesModel.KEY_FONT_LOG_AREA, fontLogArea);
        preferencesController.setProperty(PreferencesModel.KEY_LINES_NUMBER, String.valueOf(nLines));
        preferencesController.setProperty(PreferencesModel.KEY_LINES_NUMBER_LOG, String.valueOf(nLines2));
        preferencesController.setProperty(PreferencesModel.KEY_COLUMNS_NUMBER, String.valueOf(nColumns));

        preferencesController.savePreferences();
        stage.close();
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

    private void checkChanges() {
        boolean changed = false;

        if (!isSame(languageComboBox.getValue(), initLanguage)) changed = true;
        if (!isSame(fontCommandLineComboBox.getValue(), initFontCmd)) changed = true;
        if (!isSame(fontOutputAreaComboBox.getValue(), initFontOut)) changed = true;
        if (!isSame(fontLogAreaComboBox.getValue(), initFontLog)) changed = true;
        if (!isSame(linesField.getText(), initLines)) changed = true;
        if (!isSame(linesField2.getText(), initLines2)) changed = true;
        if (!isSame(columnsField.getText(), initColumns)) changed = true;


        if (saveButton != null) {
            saveButton.setDisable(!changed);
        }
    }

    private boolean isSame(String val1, String val2) {
        if (val1 == null && val2 == null) return true;
        if (val1 == null || val2 == null) return false;
        return val1.equals(val2);
    }
}