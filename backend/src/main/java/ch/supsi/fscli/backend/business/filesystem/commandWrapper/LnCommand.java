package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.IFSLnCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class LnCommand implements IFSCommand {

    private final IFSLnCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public LnCommand(IFSLnCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "ln";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (this.args == null) {
            return new CommandResult("label.wrongLnUse1", true);
        }

        //Soft Link (ln -s <target> <linkName>) -> 3 argomenti
        if (this.args.size() == 3 && this.args.get(0).equals("-s")) {
            String target = this.args.get(1);
            String linkName = this.args.get(2);

            return new CommandResult(business.lns(target, linkName), true);
        }
        //Hard Link (ln <target> <linkName>) -> 2 argomenti
        else if (this.args.size() == 2) {
            String target = this.args.get(0);
            String linkName = this.args.get(1);

            return new CommandResult(business.ln(target, linkName), true);
        }
        //Numero di argomenti errato
        else {
            return new CommandResult("label.wrongLnUse1", true);
        }
    }
}
