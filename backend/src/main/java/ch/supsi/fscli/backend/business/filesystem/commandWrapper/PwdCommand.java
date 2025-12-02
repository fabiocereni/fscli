package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class PwdCommand implements IFSCommand {

    private final IFSPwdCommandBusiness business;

    @Inject
    public PwdCommand(IFSPwdCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() { return "pwd"; }

    @Override
    public String execute(List<String> args) {
        if (!args.isEmpty()) return null;
        return business.pwd();
    }

}
