package ch.supsi.fscli.frontend.view;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Singleton
public class CommandLineView {

    private final Label commandLineLabel;
    private final Button enter;
    private final TextField commandLine;

    @Inject
    public CommandLineView() {
        this.enter = new Button("enter");
        this.enter.setId("enter");

        this.commandLineLabel = new Label("command");
        this.commandLine = new TextField();
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
