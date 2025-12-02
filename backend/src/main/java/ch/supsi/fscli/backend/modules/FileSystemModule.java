package ch.supsi.fscli.backend.modules;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class FileSystemModule extends AbstractModule {

    @Provides
    @Singleton
    DirectoryInodeBusiness provideRootDirectory() {
        return new DirectoryInodeBusiness(1L);
    }
}
