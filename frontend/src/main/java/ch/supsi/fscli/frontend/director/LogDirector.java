package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.LogEvent;
import com.google.inject.Singleton;

@Singleton
public class LogDirector extends AbstractDirector {

    public void logSavePreferences() {
        firePropertyChange(new LogEvent(this, "label.preferencesSave"));
        firePropertyChange(new LogEvent(this, "label.preferencesSaveRestart"));
    }

    public void logSaveFS(String path, boolean append) {
        String key = "label.saveFS";
        if (append)
            firePropertyChange(new LogEvent(this, key + "::" + path + "/FileSystem Simulator/saved"));
        else
            firePropertyChange(new LogEvent(this, key + "::" + path));
    }

    public void logLoadFS() {
        firePropertyChange(new LogEvent(this, "label.loadFS"));
    }

    public void logCreateFS() {
        firePropertyChange(new LogEvent(this, "label.createFS"));
    }
}