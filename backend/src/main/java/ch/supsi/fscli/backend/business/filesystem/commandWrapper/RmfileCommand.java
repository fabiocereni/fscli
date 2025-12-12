package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmfile.IFSRmfileCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class RmfileCommand implements IFSCommand {

    private final IFSRmfileCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public RmfileCommand(IFSRmfileCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "rm";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (args.size() != 1) return new CommandResult("label.wrongRmFileUse1", true);

        String fileName = args.get(0);

        if (!business.rmfile(fileName)) {
            return new CommandResult( "label.wrongRmFileUse2", true);
        }
        return null;
    }
}
