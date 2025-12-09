package ch.supsi.fscli.backend.modules;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.cd.FSCdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.cd.IFSCdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.*;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

public class FileSystemModule extends AbstractModule {

    @Provides
    @Singleton
    DirectoryInodeBusiness provideRootDirectory() {
        return new DirectoryInodeBusiness(1L);
    }

    @Override
    protected void configure() {

        Multibinder<IFSCommand> binder = Multibinder.newSetBinder(binder(), IFSCommand.class);

        binder.addBinding().to(CdCommand.class);
        binder.addBinding().to(HelpCommand.class);
        binder.addBinding().to(LnCommand.class);
        binder.addBinding().to(LsCommand.class);
        binder.addBinding().to(MkdirCommand.class);
        binder.addBinding().to(MvCommand.class);
        binder.addBinding().to(PwdCommand.class);
        binder.addBinding().to(RmdirCommand.class);
        binder.addBinding().to(RmfileCommand.class);
        binder.addBinding().to(TouchCommand.class);
        binder.addBinding().to(ClearCommand.class);
    }

}
