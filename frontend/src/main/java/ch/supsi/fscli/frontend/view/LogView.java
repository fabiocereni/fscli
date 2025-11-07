package ch.supsi.fscli.frontend.view;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

@Singleton
public class LogView {

    private final TextArea logView;

    @Inject
    public LogView() {
        this.logView = new TextArea();
        this.logView.setId("logView");
        this.logView.appendText("This is an example log text...\n");
    }



    public void initLogView(int logViewPrefRowCount) {
        this.logView.setPrefRowCount(logViewPrefRowCount);
        this.logView.setEditable(false);
    }

    public Node getNode() {
        return this.logView;
    }
}
