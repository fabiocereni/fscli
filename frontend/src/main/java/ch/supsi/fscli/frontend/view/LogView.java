package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class LogView {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static LogView myself;

    private final TextArea logView;

    private LogView() {
        this.logView = new TextArea();
        this.logView.setId("logView");
        this.logView.appendText( supportedLanguageModel.getTranslation("label.textLog")+ "\n");
    }

    public static LogView getInstance() {
        if(myself == null)
            myself = new LogView();

        return myself;
    }

    public void initLogView(int logViewPrefRowCount) {
        this.logView.setPrefRowCount(logViewPrefRowCount);
        this.logView.setEditable(false);
    }

    public Node getNode() {
        return this.logView;
    }
}
