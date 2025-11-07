package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CommandLineView {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static CommandLineView myself;

    private final Label commandLineLabel;
    private final Button enter;
    private final TextField commandLine;

    private CommandLineView() {
        this.enter = new Button(supportedLanguageModel.getTranslation("label.enter"));
        this.enter.setId("enter");

        this.commandLineLabel = new Label(supportedLanguageModel.getTranslation("label.commandLine"));
        this.commandLine = new TextField();
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
