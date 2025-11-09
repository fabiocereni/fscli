package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.backend.application.IFSCreationApplication;
import ch.supsi.fscli.frontend.event.FilesystemCreatedEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationDirector extends AbstractDirector {

    @Inject
    private IFSCreationApplication ifsCreationApplication;

    public void createFileSystem() {
        this.ifsCreationApplication.createFileSystem();
        firePropertyChange(new FilesystemCreatedEvent(this, "new", null, true));
    }

}
