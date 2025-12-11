package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.IFSTouchCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class TouchCommand implements IFSCommand {

    private final IFSTouchCommandBusiness business;

    @Inject
    public TouchCommand(IFSTouchCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() { return "touch"; }

    @Override
    public CommandResult execute(List<String> args) {
        if(args.size() != 1)
            return new CommandResult("label.wrongTouchUse1", true);
        String result  = business.touch(args.get(0));
        if(result != null)
            return new CommandResult(result, true);
        return null;
    }

}
