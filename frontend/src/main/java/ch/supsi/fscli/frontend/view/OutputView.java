package ch.supsi.fscli.frontend.view;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

@Singleton
public class OutputView {

    private final TextArea outputView;

    @Inject
    public OutputView() {
        this.outputView = new TextArea();
        this.outputView.setId("outputView");
        this.outputView.appendText("This is an example output text...\n");
    }

    public void initOutputView(int outputViewPrefRowCount) {
        this.outputView.setPrefRowCount(outputViewPrefRowCount);
        this.outputView.setEditable(false);
    }

    public Node getNode() {
        return this.outputView;
    }

}
