package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.ICommandLineController;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


@Singleton
public class CommandLineView {

    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private ISupportedLanguageController supportedLanguageController;
    @Inject
    private ICommandLineController commandLineController;

    private OutputView outputView;

    private static CommandLineView myself;

    private String fontCommandLine;

    private Label commandLineLabel;
    private Button enter;
    private TextField commandLine;

    @Inject
    public void init() {
        fontCommandLine = preferencesController.getProperty(PreferencesModel.KEY_FONT_COMMANDLINE);

        this.commandLineLabel = new Label(supportedLanguageController.getTranslation("label.commandLine"));
        this.commandLineLabel.setStyle("-fx-font-family: " + fontCommandLine + ";");

        this.commandLine = new TextField();
        this.commandLine.setStyle("-fx-font-family: " + fontCommandLine + ";");

        this.enter = new Button(supportedLanguageController.getTranslation("label.enter"));
        this.enter.setId("enter");
        this.enter.setStyle("-fx-font-family: " + fontCommandLine + ";");

        // evento per poter schiacciare il tasto invio
        EventHandler<ActionEvent> submitCommandHandler = actionEvent -> {
            String command = commandLine.getText();
            if (command.isEmpty() || outputView == null) {
                commandLine.clear();
                return;
            }

            if (outputView.getText().equals(supportedLanguageController.getTranslation("label.textOutput") + "\n"))
                outputView.clear();

            outputView.appendText("> " + command + "\n");
            String output = commandLineController.executeCommand(command);
            if (output != null && !output.isBlank())
                outputView.appendText(output + "\n");

            commandLine.clear();
        };

        this.enter.setOnAction(submitCommandHandler);
        this.commandLine.setOnAction(submitCommandHandler);
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

    public void setOutputView(OutputView outputView) {
        this.outputView = outputView;
    }
}