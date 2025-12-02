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
        if (args.size() != 1) return "rm: numero di argomenti errato - (uso: rm <nome_file>)";
        if (!business.rmdir(args.get(0))) {
            return "rm: impossibile rimuovere (non trovato o è una directory).";
        }
        return null;
    }
}
