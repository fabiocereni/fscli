package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.LogEvent;
import com.google.inject.Singleton;

@Singleton
public class LogDirector extends AbstractDirector {

    public void logSavePreferences() {
        firePropertyChange(new LogEvent(this, "Preferences saved successfully."));
        firePropertyChange(new LogEvent(this, "Please restart the application for changes to take full effect."));
    }

    public void logSaveFS() {
        firePropertyChange(new LogEvent(this, "FS saved successfully."));
    }

    public void logLoadFS() {
        firePropertyChange(new LogEvent(this, "FS loaded successfully."));
    }

    public void logCreateFS() {
        firePropertyChange(new LogEvent(this, "FS created successfully."));
    }
}