package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.IFSLnCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class LnCommand implements IFSCommand {

    private final IFSLnCommandBusiness business;

    @Inject
    public LnCommand(IFSLnCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "ln";
    }

    @Override
    public String execute(List<String> args) {
        return "";
    }
}
