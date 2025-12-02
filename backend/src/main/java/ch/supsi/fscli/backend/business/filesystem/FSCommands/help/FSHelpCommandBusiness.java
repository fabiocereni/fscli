package ch.supsi.fscli.backend.business.filesystem.FSCommands.help;

import com.google.inject.Singleton;

@Singleton
public class FSHelpCommandBusiness implements IFSHelpCommandBusiness {

    @Override
    public boolean help() {
        return true;
    }
}
