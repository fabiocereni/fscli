package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.LoadFSEvent;
import com.google.inject.Singleton;

@Singleton
public class FSLoadDirectory extends AbstractDirector {
    public void manageFileSystemLoading() {
        firePropertyChange(new LoadFSEvent(this, "load", null, true));
    }
}
