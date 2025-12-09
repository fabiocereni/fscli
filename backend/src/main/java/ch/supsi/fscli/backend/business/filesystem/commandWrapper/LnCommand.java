package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.IFSLnCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class LnCommand implements IFSCommand {

    private final IFSLnCommandBusiness business;

    @Inject
    public LnCommand(IFSLnCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "ln";
    }

    @Override
    public CommandResult execute(List<String> args) {
            if (args.size() == 3 && args.get(0).equals("-s")) {
                String target = args.get(1);
                String linkName = args.get(2);

                return new CommandResult(business.lns(target, linkName),  true);
            } else if (args.size() == 2) {
                String target = args.get(0);
                String linkName = args.get(1);

                return new CommandResult(business.ln(target, linkName), true);
            }
            else {
                return new CommandResult("label.wrongLnUse1", true);
            }
    }
}
