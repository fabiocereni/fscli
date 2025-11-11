package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
@Singleton
public class CommandLineView {

    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private ISupportedLanguageController supportedLanguageController;

    private static CommandLineView myself;

    private String fontCommandLine;

    private Label commandLineLabel;
    private Button enter;
    private TextField commandLine;

    @Inject
    public void init() {
        fontCommandLine = preferencesController.getProperty(PreferencesModel.KEY_FONT_COMMANDLINE);

        this.enter = new Button(supportedLanguageController.getTranslation("label.enter"));
        this.enter.setId("enter");
        this.enter.setStyle("-fx-font-family: " + fontCommandLine + ";");

        this.commandLineLabel = new Label(supportedLanguageController.getTranslation("label.commandLine"));
        this.commandLineLabel.setStyle("-fx-font-family: " + fontCommandLine + ";");

        this.commandLine = new TextField();
        this.commandLine.setStyle("-fx-font-family: " + fontCommandLine + ";");
    }

    public void initCommandLineView(int commandLinePrefColumnCount) {
        this.commandLine.setPrefColumnCount(commandLinePrefColumnCount);
    }

    public Label getCommandLineLabel() {
        return commandLineLabel;
    }

    public Button getEnter() {
        return enter;
    }

    public TextField getCommandLine() {
        return commandLine;
    }
}