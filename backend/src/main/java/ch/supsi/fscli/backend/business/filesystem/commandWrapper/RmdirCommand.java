package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class RmdirCommand implements IFSCommand {

    private final IFSRmdirCommandBusiness business;

    @Inject
    public RmdirCommand(IFSRmdirCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "rmdir";
    }

    @Override
    public CommandResult execute(List<String> args) {
        if (args.size() != 1) return new CommandResult("label.wrongRmUse1", true);
        if (!business.rmdir(args.get(0))) {
            return new CommandResult("label.wrongRmUse2", true);
        }
        return null;
    }
}
