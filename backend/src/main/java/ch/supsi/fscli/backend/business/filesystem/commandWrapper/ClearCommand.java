package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class ClearCommand implements IFSCommand {

    @Inject
    public ClearCommand() {
    }

    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public String execute(List<String> args) {
        if (!args.isEmpty()) {
            return "clear: il comando non accetta argomenti.";
        }
        return "clear";
    }
}
