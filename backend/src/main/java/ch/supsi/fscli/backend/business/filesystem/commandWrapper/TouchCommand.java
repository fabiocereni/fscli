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
    public String execute(List<String> args) {
        if(args.size() != 1)
            return "touch: numero di argomenti errato - (uso: touch <nome_file>)";
        if(!business.touch(args.get(0)))
            return "touch: impossibile creare il file.";
        return null;
    }

}
