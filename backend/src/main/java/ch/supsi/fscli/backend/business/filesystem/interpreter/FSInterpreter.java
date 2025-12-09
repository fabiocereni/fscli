package ch.supsi.fscli.backend.business.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
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
    public CommandResult execute(String commandLine) {
        if (commandLine == null || commandLine.isBlank())
            return null;
        String[] tokens = commandLine.trim().split("\\s+");
        String commandName = tokens[0].toLowerCase();

        List<String> args = Arrays.stream(tokens)
                .skip(1)
                .collect(Collectors.toList());
        IFSCommand command = commands.get(commandName);
        if (command == null)
            return new CommandResult( "label.commandNotFound", true);
        return command.execute(args);
    }
}
