package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.FilesystemCreatedEvent;
import com.google.inject.Singleton;

@Singleton
public class FSCreationDirector extends AbstractDirector {
    public void manageFileSystemCreation() {
        firePropertyChange(new FilesystemCreatedEvent(this, "new", null, true));
    }
}
