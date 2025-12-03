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
    public String execute(List<String> args) {
        if (args.size() != 1) return "label.wrongRmFileUse1";
        if (!business.rmfile(args.get(0))) {
            return "label.wrongRmFileUse2";
        }
        return null;
    }
}
