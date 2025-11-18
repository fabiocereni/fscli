package ch.supsi.fscli.backend.business.FSCommands.help;

import ch.supsi.fscli.backend.business.i18n.SupportedLanguageBusiness;

public class FSHelpCommandBusiness implements IFSHelpCommandBusiness {

    private static FSHelpCommandBusiness myself;

    private final SupportedLanguageBusiness supportedLanguageBusiness = SupportedLanguageBusiness.getInstance();

    private FSHelpCommandBusiness() {}

    public static FSHelpCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSHelpCommandBusiness();
        return myself;
    }

    @Override
    public boolean help() {
        return false;
    }
}
