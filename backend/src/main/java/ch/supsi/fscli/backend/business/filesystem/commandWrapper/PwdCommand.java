package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PwdCommand implements IFSCommand {

    private final IFSPwdCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public PwdCommand(IFSPwdCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() { return "pwd"; }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (!args.isEmpty()) return null;
        return new CommandResult(business.pwd(), false );
    }
}
