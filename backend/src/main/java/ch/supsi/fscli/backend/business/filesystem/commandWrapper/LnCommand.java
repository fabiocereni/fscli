package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.IFSLnCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class LnCommand implements IFSCommand {

    private final IFSLnCommandBusiness business;

    private List<String> args = new ArrayList<>();

    @Inject
    public LnCommand(IFSLnCommandBusiness business) {
        this.business = business;
    }

    @Override
    public String getCommandName() {
        return "ln";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String execute() {
        if (this.args == null) {
            return "label.wrongLnUse1";
        }

        try {
            // Caso 1: Soft Link (ln -s <target> <linkName>) -> 3 argomenti
            if (this.args.size() == 3 && this.args.get(0).equals("-s")) {
                String target = this.args.get(1);
                String linkName = this.args.get(2);

                String result = business.lns(target, linkName);
                if (!result.isEmpty()) {
                    return result;
                }
                return null;
            }
            // Caso 2: Hard Link (ln <target> <linkName>) -> 2 argomenti
            else if (this.args.size() == 2) {
                String target = this.args.get(0);
                String linkName = this.args.get(1);

                boolean success = business.ln(target, linkName);

                if (!success) {
                    //
                }
                return null; // Successo
            }
            // Caso 3: Numero di argomenti errato (es. 0, 1, 4+) o flag errato
            else {
                return "label.wrongLnUse1";
            }

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }
}
