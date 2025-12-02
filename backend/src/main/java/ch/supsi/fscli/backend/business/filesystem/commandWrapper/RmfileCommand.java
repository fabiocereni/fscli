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
        if (args.size() != 1) return "rmfile: numero di argomenti errato - (uso: rmfile <nome_file>)";
        if (!business.rmfile(args.get(0))) {
            return "rmfile: impossibile rimuovere il file (non trovato o è una directory).";
        }
        return null;
    }
}
