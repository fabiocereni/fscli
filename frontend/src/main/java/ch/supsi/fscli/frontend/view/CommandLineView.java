package ch.supsi.fscli.frontend.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CommandLineView {


    private static CommandLineView myself;

    private final Label commandLineLabel;
    private final Button enter;
    private final TextField commandLine;


    private CommandLineView() {
        this.enter = new Button("enter");
        this.enter.setId("enter");

        this.commandLineLabel = new Label("command");
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
