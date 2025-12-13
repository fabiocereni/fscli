package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class RmdirCommand implements IFSCommand {

    private final IFSRmdirCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public RmdirCommand(IFSRmdirCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "rmdir";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (args.isEmpty()) return new CommandResult("label.wrongRmUse1", true);

        boolean errorOccurred = false;

        for (String directoryName : args) {
            boolean success = business.rmdir(directoryName);
            if (!success) errorOccurred = true;
        }

        if (errorOccurred) return new CommandResult("label.wrongRmUse2", true);
        return null;
    }
}
