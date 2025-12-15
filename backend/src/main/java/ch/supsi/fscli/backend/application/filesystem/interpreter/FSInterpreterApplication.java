package ch.supsi.fscli.backend.application.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.backend.business.filesystem.interpreter.IFSInterpreter;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSInterpreterApplication implements IFSInterpreterApplication {

    private final IFSInterpreter interpreter;

    @Inject
    public FSInterpreterApplication(IFSInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public CommandResult execute(String commandLine) {
        return interpreter.execute(commandLine);
    }
}
