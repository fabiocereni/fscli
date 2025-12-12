package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.help.IFSHelpCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class HelpCommand implements IFSCommand {

    private final IFSHelpCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public HelpCommand(IFSHelpCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (!args.isEmpty()) {
            return new CommandResult("label.wrongHelpUse", true);
        }
        business.help(); // sistemare
        return new CommandResult("label.infoHelp", true);
    }
}
