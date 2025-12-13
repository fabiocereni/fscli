package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.cd.IFSCdCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CdCommand implements IFSCommand {
    private final IFSCdCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public CdCommand(IFSCdCommandBusiness business) {
        this.business = business;
    }

    public void setArgs(List<String> args) { this.args = args; }

    @Override
    public String getCommandName() { return "cd"; }

    @Override
    public CommandResult execute() {
        if (this.args.isEmpty()) {
            if (!business.cd("/")) {
                return new CommandResult("label.wrongCdUse2", true);
            }
            return null;
        }

        if (this.args.size() > 1) {
            return new CommandResult("label.wrongCdUse1", true);
        }

        String path = this.args.get(0);

        if (!business.cd(path)) {
            return new CommandResult("label.wrongCdUse2", true);
        }
        return null;
    }
}