package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.IFSInterpreter;
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
    public String getCurrentpath() {
        return interpreter.getCurrentpath();
    }

    @Override
    public String execute(String commandLine) {
        return interpreter.execute(commandLine);
    }
}
