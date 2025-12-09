package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmfile.IFSRmfileCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class RmfileCommand implements IFSCommand {

    private final IFSRmfileCommandBusiness business;

    @Inject
    public RmfileCommand(IFSRmfileCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "rm";
    }

    @Override
    public CommandResult execute(List<String> args) {
        if (args.size() != 1) return new CommandResult("label.wrongRmFileUse1", true);
        if (!business.rmfile(args.get(0))) {
            return new CommandResult( "label.wrongRmFileUse2", true);
        }
        return null;
    }
}
