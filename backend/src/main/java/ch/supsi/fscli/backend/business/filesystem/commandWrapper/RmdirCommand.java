package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class RmdirCommand implements IFSCommand {

    private final IFSRmdirCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public RmdirCommand(IFSRmdirCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "rmdir";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String execute() {
        if (args.size() != 1) return "label.wrongRmUse1";

        String directoryName = args.get(0);

        if (!business.rmdir(directoryName)) {
            return "label.wrongRmUse2";
        }
        return null;
    }
}
