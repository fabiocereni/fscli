package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.mv.IFSMvCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class MvCommand implements IFSCommand {

    private final IFSMvCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public MvCommand(IFSMvCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "mv";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (args.size() != 2)
            return new CommandResult("label.wrongMvUse1", true);

        String source = this.args.get(0);
        String destination = this.args.get(1);

        if (!business.mv(source, destination)) {
            return new CommandResult( "label.wrongMvUse2", true);
        }
        return null;
    }
}
