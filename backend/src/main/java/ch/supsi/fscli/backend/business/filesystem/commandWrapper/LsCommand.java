package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.IFSLsCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class LsCommand implements IFSCommand {

    private final IFSLsCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public LsCommand(IFSLsCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String execute() {
        boolean showInode = false;
        String path = null;

        // Lista temporanea per gli argomenti che non sono opzioni (flag)
        List<String> cleanArgs = new ArrayList<>();

        // Se args è null (nessun argomento), la lista cleanArgs resta vuota e path resta null -> OK (LS sulla directory corrente)
        if (this.args != null) {
            for (String arg : this.args) {
                if (arg.equals("-i")) {
                    showInode = true;
                } else {
                    cleanArgs.add(arg);
                }
            }
        }

        // Controllo validità argomenti (LS accetta al massimo 1 path)
        if (cleanArgs.size() > 1) {
            return "label.wrongLsUse1";
        }

        // Se c'è un path specificato, lo prendiamo
        if (!cleanArgs.isEmpty()) {
            path = cleanArgs.get(0);
        }

        // Chiamata alla logica di business
        return business.ls(path, showInode);
    }
}
