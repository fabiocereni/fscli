package ch.supsi.fscli.backend.business.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.*;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;


@Singleton
public class FSInterpreter implements IFSInterpreter {

    private final Map<String, IFSCommand> commands = new HashMap<>();
    private final IFSPwdCommandBusiness pwdCommandBusiness;


    @Inject
    public FSInterpreter(IFSPwdCommandBusiness pwdCommandBusiness,
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
        this.pwdCommandBusiness = pwdCommandBusiness;

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