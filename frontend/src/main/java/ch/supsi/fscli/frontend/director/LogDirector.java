package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.LogEvent;
import ch.supsi.fscli.frontend.model.persistence.IFSDataSaverModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LogDirector extends AbstractDirector {
    private final IFSDataSaverModel model;

    @Inject
    public LogDirector(IFSDataSaverModel model) {
        this.model = model;
    }

    public void logSavePreferences() {
        firePropertyChange(new LogEvent(this, "label.preferencesSave"));
        firePropertyChange(new LogEvent(this, "label.preferencesSaveRestart"));
    }

    public void logSaveFS() {
        String key = "label.saveFS";
        firePropertyChange(new LogEvent(this, key + "::" + model.getPathToPrint()));
    }

    public void logLoadFS() {
        firePropertyChange(new LogEvent(this, "label.loadFS"));
    }

    public void logCreateFS() {
        firePropertyChange(new LogEvent(this, "label.createFS"));
    }
}