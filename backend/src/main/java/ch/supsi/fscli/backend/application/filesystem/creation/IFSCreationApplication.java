package ch.supsi.fscli.backend.application.filesystem.creation;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSCreationApplication.class)
public interface IFSCreationApplication {
    void createFileSystem();
}
