package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ClearCommand implements IFSCommand {

    private List<String> args = new ArrayList<>();

    @Inject
    public ClearCommand() {
    }

    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String execute() {
        if (!args.isEmpty()) return "label.wrongClearUse";
        return "clear";
    }

}
