package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class OutputView {

    private final IPreferencesModel preferencesModel = PreferencesModel.getInstance();
    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static OutputView myself;

    private String fontOutput;

    private final TextArea outputView;

    private OutputView() {
        fontOutput = preferencesModel.getProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA);

        this.outputView = new TextArea();
        this.outputView.setId("outputView");
        this.outputView.appendText(supportedLanguageModel.getTranslation("label.textOutput") + "\n");
        this.outputView.setStyle("-fx-font-family: " + fontOutput + ";");
    }

    public static OutputView getInstance() {
        if(myself == null)
            myself = new OutputView();

        return myself;
    }

    public void initOutputView(int prefRowCount) {
        outputView.setId("outputView");
        outputView.setEditable(false);
        outputView.setWrapText(true);
        outputView.setPrefRowCount(prefRowCount);
    }

    public Node getNode() {
        return this.outputView;
    }

    public void appendText(String text) {
        if (outputView != null) {
            outputView.appendText(text + "\n");
        }
    }

    public void clear() {
        if (outputView != null) outputView.clear();
    }

    // Utile per MainFx
    public TextArea getTextArea() {
        return outputView;
    }
}