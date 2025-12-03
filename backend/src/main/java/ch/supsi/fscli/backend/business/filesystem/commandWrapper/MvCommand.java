package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.mv.IFSMvCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class MvCommand implements IFSCommand {

    private final IFSMvCommandBusiness business;

    @Inject
    public MvCommand(IFSMvCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "mv";
    }

    @Override
    public String execute(List<String> args) {
        if (args.size() != 2)
            return "label.wrongMvUse1";
        if (!business.mv(args.get(0), args.get(1))) {
            return "label.wrongMvUse2";
        }
        return null;

    }
}
