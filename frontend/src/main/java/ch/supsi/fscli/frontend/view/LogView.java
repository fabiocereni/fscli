package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.event.LogEvent;
import ch.supsi.fscli.frontend.event.SaveEvent;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Singleton
public class LogView implements PropertyChangeListener {

    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private ISupportedLanguageController supportedLanguageController;

    private String fontLog;

    private TextArea logView;

    @Inject
    public void init() {
        fontLog = preferencesController.getProperty(PreferencesModel.KEY_FONT_LOG_AREA);

        this.logView = new TextArea();
        this.logView.setId("logView");
        this.logView.setStyle("-fx-font-family: " + fontLog + ";");
        this.logView.appendText(supportedLanguageController.getTranslation("label.langInfo") + " " + supportedLanguageController.getLanguageTagSelected());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt instanceof LogEvent logEvent) {

            if (logView != null) {

                String msg = logEvent.getMessage();

                // Splitta key e parametro
                String[] parts = msg.split("::", 2);
                String key = parts[0];
                String param = (parts.length > 1) ? parts[1] : "";

                // Traduzione
                String translated = supportedLanguageController.getTranslation(key);

                // Aggiungi parametro (path) alla fine
                if (!param.isEmpty())
                    translated = translated + " " + param;

                logView.appendText("\n" + translated);
            }
        }
    }

    public void initLogView(int logViewPrefRowCount) {
        this.logView.setPrefRowCount(logViewPrefRowCount);
        this.logView.setEditable(false);
    }

    public Node getNode() {
        return this.logView;
    }

}