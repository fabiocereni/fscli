package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.IFSLsCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public CommandResult execute() {
        boolean showInode = false;
        List<String> targets = new ArrayList<>();

        // 1. Parsing: Separiamo opzioni (-i) dai percorsi
        if (this.args != null) {
            for (String arg : this.args) {
                if (arg.equals("-i")) {
                    showInode = true;
                } else {
                    targets.add(arg);
                }
            }
        }

        // Caso A: Nessun target specificato -> ls sulla cartella corrente (.)
        if (targets.isEmpty()) {
            CommandResult result = business.ls(null, showInode);
            if (result == null) return null;

            String content = result.getContent();
            // Se c'è un errore (es. label.error), lo ritorniamo diretto
            if (isErrorMessage(content)) {
                return result;
            }
            // Altrimenti formattiamo
            return new CommandResult(formatHierarchy(".", content), false);
        }

        // Caso B: Uno o più target (es. ls folder1 folder2)
        StringBuilder finalOutput = new StringBuilder();

        for (int i = 0; i < targets.size(); i++) {
            String path = targets.get(i);
            CommandResult result = business.ls(path, showInode);
            String content = (result != null) ? result.getContent() : "";

            if (isErrorMessage(content)) {
                finalOutput.append(content);
            } else {
                // Applichiamo lo stile gerarchico
                finalOutput.append(formatHierarchy(path, content));
            }

            // Aggiungiamo "a capo" tra un blocco e l'altro (ma non alla fine)
            if (i < targets.size() - 1) {
                finalOutput.append("\n");
            }
        }

        return new CommandResult(finalOutput.toString(), false);
    }

    /**
     * Helper per formattare l'output nello stile:
     * NomeCartella
     * !- Contenuto1
     * !- Contenuto2
     */
    private String formatHierarchy(String parentName, String content) {
        if (content.isEmpty()) {
            return parentName; // Cartella vuota, stampa solo il nome
        }

        StringBuilder sb = new StringBuilder();
        sb.append(parentName).append("\n");

        // Prende ogni riga dell'output originale e ci mette davanti "!- "
        String indentedContent = content.lines()
                .map(line -> "!- " + line)
                .collect(Collectors.joining("\n"));

        sb.append(indentedContent);
        return sb.toString();
    }

    // Piccolo controllo per evitare di formattare i messaggi di errore come gerarchie
    private boolean isErrorMessage(String content) {
        return content.startsWith("label.") || content.contains("cannot access");
    }
}