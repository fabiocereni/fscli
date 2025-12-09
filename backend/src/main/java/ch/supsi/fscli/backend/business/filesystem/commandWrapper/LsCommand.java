package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.IFSLsCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class LsCommand implements IFSCommand {

    private final IFSLsCommandBusiness business;

    @Inject
    public LsCommand(IFSLsCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public CommandResult execute(List<String> args) {
        boolean showInode = false;
        String path = null;
        List<String> cleanArgs = new ArrayList<>();
        for (String arg : args) {
            if (arg.equals("-i")) {
                showInode = true;
            } else {
                cleanArgs.add(arg);
            }
        }
        if (cleanArgs.size() > 1) {
            return new CommandResult( "label.wrongLsUse1", true);
        }
        if (!cleanArgs.isEmpty()) {
            path = cleanArgs.get(0);
        }
        return business.ls(path, showInode);
    }
}
