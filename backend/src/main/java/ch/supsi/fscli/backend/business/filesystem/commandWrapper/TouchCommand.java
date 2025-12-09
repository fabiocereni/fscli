package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.IFSTouchCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class TouchCommand implements IFSCommand {

    private final IFSTouchCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public TouchCommand(IFSTouchCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() { return "touch"; }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String execute() {
        if(args.isEmpty())
            return "label.wrongTouchUse1";

        String lastError = null;

        for (String fileName : args) {
            String result = business.touch(fileName);
            if(!result.isEmpty())
                lastError = result;
        }

        return lastError;
    }

}
