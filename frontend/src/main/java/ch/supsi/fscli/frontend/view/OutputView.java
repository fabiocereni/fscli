package ch.supsi.fscli.frontend.view;

import javafx.scene.control.TextArea;
import javafx.scene.Node;

public class OutputView {

    private static OutputView myself;
    private TextArea outputArea;

    private OutputView() {}

    public static OutputView getInstance() {
        if (myself == null) myself = new OutputView();
        return myself;
    }

    public void initOutputView(int prefRowCount) {
        outputArea = new TextArea();
        outputArea.setId("outputView");
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefRowCount(prefRowCount);
        outputArea.appendText("FSCLI ready. Type 'help' for available commands.\n");
    }

    public Node getNode() {
        return outputArea;
    }

    public void appendText(String text) {
        if (outputArea != null) {
            outputArea.appendText(text + "\n");
        }
    }

    public void clear() {
        if (outputArea != null) outputArea.clear();
    }

    // Utile per MainFx
    public TextArea getTextArea() {
        return outputArea;
    }
}