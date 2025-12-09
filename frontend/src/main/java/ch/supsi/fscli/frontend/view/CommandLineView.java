package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.frontend.controller.filesystem.ICommandLineController;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.director.FSLoadDirectory;
import ch.supsi.fscli.frontend.director.InputDirector;
import ch.supsi.fscli.frontend.event.FilesystemCreatedEvent;
import ch.supsi.fscli.frontend.event.LoadFSEvent;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


@Singleton
public class CommandLineView implements PropertyChangeListener {

    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private ISupportedLanguageController supportedLanguageController;
    @Inject
    private ICommandLineController commandLineController;
    @Inject
    private FSCreationDirector fsCreationDirector;
    @Inject
    private FSLoadDirectory FSLoadDirectory;
    @Inject
    private InputDirector inputDirector;

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
        this.commandLine.setDisable(true);
        this.commandLine.setId("commandLineView");

        this.enter = new Button(supportedLanguageController.getTranslation("label.enter"));
        this.enter.setId("enter");
        this.enter.setStyle("-fx-font-family: " + fontCommandLine + ";");
        this.enter.setDisable(true);

        // evento per poter schiacciare il tasto invio
        EventHandler<ActionEvent> submitCommandHandler = actionEvent -> {
            String command = commandLine.getText();
            if (command.isEmpty() || outputView == null) {
                commandLine.clear();
                return;
            }

            if (outputView.getText().equals(supportedLanguageController.getTranslation("label.textOutput") + "\n"))
                outputView.clear();

            outputView.appendText(commandLineController.getCurrentPath() + "> " + command + "\n");

            CommandResult output = commandLineController.executeCommand(command);

            if(output != null) {
                if ("clear".equals(output.getContent())) {
                    outputView.clear();
                } else if ("label.infoHelp".equals(output.getContent()) && output.isTranslatable()) {
                    outputView.appendText(supportedLanguageController.getTranslation(output.getContent()));
                } else if (output.getContent() != null && !output.getContent().isBlank()) {
                    if (output.isTranslatable())
                        outputView.appendText(supportedLanguageController.getTranslation(output.getContent()) + "\n");
                    else
                        outputView.appendText(output.getContent() + "\n");
                }
            }

            // da decidere
            this.inputDirector.manageInput();

            commandLine.clear();
        };

        this.enter.setOnAction(submitCommandHandler);
        this.commandLine.setOnAction(submitCommandHandler);

        this.fsCreationDirector.addPropertyChangeListener(this);
        this.FSLoadDirectory.addPropertyChangeListener(this);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if(evt instanceof FilesystemCreatedEvent) {
            commandLine.setDisable(false);
            enter.setDisable(false);
        }

        if (evt instanceof LoadFSEvent) {
            commandLine.setDisable(false);
            enter.setDisable(false);
        }
    }

    public Node getNode() {
        return this.commandLine;
    }
}
