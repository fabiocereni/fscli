package ch.supsi.fscli.backend.business.FSCommands.help;

import com.google.inject.Singleton;

@Singleton
public class FSHelpCommandBusiness implements IFSHelpCommandBusiness {

    @Override
    public boolean help() {
        return true;
    }
}
