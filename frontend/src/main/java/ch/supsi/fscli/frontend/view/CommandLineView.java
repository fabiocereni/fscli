package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CommandLineView {

    private final IPreferencesModel preferencesModel = PreferencesModel.getInstance();
    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static CommandLineView myself;

    private String fontCommandLine;

    private final Label commandLineLabel;
    private final Button enter;
    private final TextField commandLine;

    private CommandLineView() {
        fontCommandLine = preferencesModel.getProperty(PreferencesModel.KEY_FONT_COMMANDLINE);

        this.enter = new Button(supportedLanguageModel.getTranslation("label.enter"));
        this.enter.setId("enter");
        this.enter.setStyle("-fx-font-family: " + fontCommandLine + ";");

        this.commandLineLabel = new Label(supportedLanguageModel.getTranslation("label.commandLine"));
        this.commandLineLabel.setStyle("-fx-font-family: " + fontCommandLine + ";");

        this.commandLine = new TextField();
        this.commandLine.setStyle("-fx-font-family: " + fontCommandLine + ";");
    }

    public static CommandLineView getInstance() {
        if(myself == null)
            myself = new CommandLineView();

        return myself;
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
