package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.cd.IFSCdCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class CdCommand implements IFSCommand {
    private final IFSCdCommandBusiness business;

    @Inject
    public CdCommand(IFSCdCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() { return "cd"; }

    @Override
    public String execute(List<String> args) {
        if (args.size() != 1) return "label.wrongCdUse1";
        if (!business.cd(args.get(0))) {
            return "label.wrongCdUse2";
        }
        return null;
    }
}