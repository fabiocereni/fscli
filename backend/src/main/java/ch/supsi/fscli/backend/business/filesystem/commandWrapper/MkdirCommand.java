package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.mkdir.IFSMkdirCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class MkdirCommand implements IFSCommand {

    private final IFSMkdirCommandBusiness business;

    @Inject
    public MkdirCommand(IFSMkdirCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public CommandResult execute(List<String> args) {
        if (args.size() != 1) return new CommandResult("label.wrongMkdirUse1", true);
        if (!business.mkdir(args.get(0))) {
            return new CommandResult("label.wrongMkdirUse2",  true);
        }
        return null;
    }
}
