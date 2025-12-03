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
    public String execute(List<String> args) {
        if (args.size() != 1) return "label.wrongRmUse1";
        if (!business.rmdir(args.get(0))) {
            return "label.wrongRmUse2";
        }
        return null;
    }
}
