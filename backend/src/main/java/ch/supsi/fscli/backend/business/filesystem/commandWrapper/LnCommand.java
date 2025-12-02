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
    public String execute(List<String> args) {
        try {
            if (args.size() == 3 && args.get(0).equals("-s")) {
                String target = args.get(1);
                String linkName = args.get(2);

                business.lns(target, linkName);
                return null;
            } else if (args.size() == 2) {
                String target = args.get(0);
                String linkName = args.get(1);

                business.ln(target, linkName);
                return null;
            }
            else {
                return "ln: numero di argomenti errato - (uso: ln [-s] <target> <linkName>)";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
