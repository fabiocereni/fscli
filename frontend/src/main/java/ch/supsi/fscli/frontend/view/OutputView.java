package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.event.FilesystemCreatedEvent;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Singleton
public class OutputView implements PropertyChangeListener {

    @Inject
    private IPreferencesModel preferencesModel;
    @Inject
    private ISupportedLanguageController supportedLanguageController;
    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private FSCreationDirector fsCreationDirector;

    private String fontOutput;

    private TextArea outputView;

    @Inject
    public void init() {
        this.outputView = new TextArea();
        outputView.setId("outputView");
        outputView.setEditable(false);
        outputView.setWrapText(true);
        outputView.setPrefRowCount(Integer.parseInt(preferencesController.getProperty(PreferencesModel.KEY_LINES_NUMBER)));
        fontOutput = preferencesController.getProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA);
        this.outputView.setId("outputView");
        this.outputView.setText(supportedLanguageController.getTranslation("label.textOutput") + "\n");
        this.outputView.setStyle("-fx-font-family:" + fontOutput + ";");

        this.fsCreationDirector.addPropertyChangeListener(this);
    }

    public void clear() {
        outputView.clear();
    }

    public void appendText(String text) {
        outputView.appendText(text);
    }

    public String getText() {
        return outputView.getText();
    }

    public Node getNode() {
        return this.outputView;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt instanceof FilesystemCreatedEvent) {
            clear();
        }
    }
}
