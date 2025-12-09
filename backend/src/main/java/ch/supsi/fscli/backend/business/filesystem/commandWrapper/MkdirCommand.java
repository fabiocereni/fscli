package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.mkdir.IFSMkdirCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class MkdirCommand implements IFSCommand {

    private final IFSMkdirCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public MkdirCommand(IFSMkdirCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String execute() {
        if (args.isEmpty()) return "label.wrongMkdirUse1";

        boolean errorsOccurred = false;
        for (String directoryName : args) {
            boolean success = business.mkdir(directoryName);
            if (!success) errorsOccurred = true;
        }

        if (errorsOccurred) return "label.wrongMkdirUse2";
        return null;
    }

}
