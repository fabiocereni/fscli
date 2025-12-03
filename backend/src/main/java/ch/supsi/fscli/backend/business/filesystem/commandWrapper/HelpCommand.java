package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.help.IFSHelpCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class HelpCommand implements IFSCommand {

    private final IFSHelpCommandBusiness business;

    @Inject
    public HelpCommand(IFSHelpCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String execute(List<String> args) {
        if (args.isEmpty())
            return "label.infoHelp";
        return "label.wrongHelpUse";
    }
}
