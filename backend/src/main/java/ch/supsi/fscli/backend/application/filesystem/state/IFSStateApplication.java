package ch.supsi.fscli.backend.application.filesystem.state;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSStateApplication.class)
public interface IFSStateApplication {
    boolean changeSavedStateAndGet();
}
