package ch.supsi.fscli.backend.business.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.IFSCommand;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class FSInterpreter implements IFSInterpreter {

    private final Map<String, IFSCommand> commands;

    private final IFSPwdCommandBusiness pwdCommandBusiness;

    @Inject
    public FSInterpreter(Set<IFSCommand> commands, IFSPwdCommandBusiness pwdCommandBusiness) {
        this.pwdCommandBusiness = pwdCommandBusiness;
        this.commands = new HashMap<>();
        for (IFSCommand command : commands) {
            this.commands.put(command.getCommandName().toLowerCase(), command);
        }
    }

    @Override
    public String getCurrentpath() {
        return pwdCommandBusiness.pwd();
    }

    @Override
    public String execute(String commandLine) {
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
            return "label.commandNotFound";

        command.setArgs(args);
        return command.execute();
    }
}
