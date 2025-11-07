package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class OutputView {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static OutputView myself;

    private final TextArea outputView;

    private OutputView() {
        this.outputView = new TextArea();
        this.outputView.setId("outputView");
        this.outputView.appendText(supportedLanguageModel.getTranslation("label.textOutput") + "\n");
    }

    public static OutputView getInstance() {
        if(myself == null)
            myself = new OutputView();

        return myself;
    }

    public void initOutputView(int outputViewPrefRowCount) {
        this.outputView.setPrefRowCount(outputViewPrefRowCount);
        this.outputView.setEditable(false);
    }

    public Node getNode() {
        return this.outputView;
    }

}
