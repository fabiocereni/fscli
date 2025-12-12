package ch.supsi.fscli.backend.business.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.*;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.IFSCommand;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;
import java.util.regex.Pattern;


@Singleton
public class FSInterpreter implements IFSInterpreter {

    private final Map<String, IFSCommand> commands = new HashMap<>();
    private final FileSystem fileSystem;


    @Inject
    public FSInterpreter(FileSystem fileSystem,
                         CdCommand cd,
                         HelpCommand help,
                         LnCommand ln,
                         LsCommand ls,
                         MkdirCommand mkdir,
                         MvCommand mv,
                         PwdCommand pwd,
                         RmdirCommand rmdir,
                         RmfileCommand rmfile,
                         TouchCommand touch,
                         ClearCommand clear) {
        this.fileSystem = fileSystem;
        addCommand(cd);
        addCommand(help);
        addCommand(ln);
        addCommand(ls);
        addCommand(mkdir);
        addCommand(mv);
        addCommand(pwd);
        addCommand(rmdir);
        addCommand(rmfile);
        addCommand(touch);
        addCommand(clear);
    }

    private void addCommand(IFSCommand command) {
        this.commands.put(command.getCommandName().toLowerCase(), command);
    }

    @Override
    public CommandResult execute(String commandLine) {
        if (commandLine == null || commandLine.isBlank())
            return null;

        StringTokenizer tokenizer = new StringTokenizer(commandLine);
        if (!tokenizer.hasMoreTokens()) return null;

        String commandName = tokenizer.nextToken().toLowerCase();
        List<String> args = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            args.add(tokenizer.nextToken());
        }

        IFSCommand command = commands.get(commandName);

        if (command == null)
            return new CommandResult( "label.commandNotFound", true);

        List<String> expandedArgs = expandArgs(args);
        command.setArgs(expandedArgs);
        return command.execute();
    }

    private List<String> expandArgs(List<String> args) {
        List<String> result = new ArrayList<>();

        // Otteniamo i nomi dei file nella cartella corrente per fare i confronti
        Set<String> filesInDir = fileSystem.getCurrentWorkingDirectory().getEntries().keySet();

        for (String arg : args) {
            if (arg.contains("*")) {
                // Convertiamo il pattern "glob" (es. *.txt) in Regex Java (es. ^.*\.txt$)
                // 1. Escape del punto (.) che in regex significa "qualsiasi carattere"
                // 2. Sostituzione dell'asterisco (*) con ".*" (qualsiasi sequenza di caratteri)
                String regex = "^" + arg.replace(".", "\\.").replace("*", ".*") + "$";
                Pattern pattern = Pattern.compile(regex);

                List<String> matches = new ArrayList<>();
                for (String fileName : filesInDir) {
                    // Escludiamo i riferimenti speciali . e ..
                    if (fileName.equals(".") || fileName.equals("..")) continue;

                    // Se il nome del file corrisponde alla regex, lo aggiungiamo
                    if (pattern.matcher(fileName).matches()) {
                        matches.add(fileName);
                    }
                }

                // Se abbiamo trovato corrispondenze, le aggiungiamo (ordinate)
                if (!matches.isEmpty()) {
                    Collections.sort(matches);
                    result.addAll(matches);
                } else {
                    // Se non ci sono match (es. *.pdf e non ho pdf),
                    // le shell di solito lasciano l'argomento letterale "*.pdf"
                    result.add(arg);
                }
            } else {
                // Nessun asterisco, aggiungiamo l'argomento così com'è
                result.add(arg);
            }
        }
        return result;
    }
}