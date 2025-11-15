package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.FSInterpreter;
import ch.supsi.fscli.backend.business.IFSInterpreter;

public class FSInterpreterApplication implements IFSInterpreterApplication {

    private static FSInterpreterApplication myself;

    private final IFSInterpreter interpreter = FSInterpreter.getInstance();

    private FSInterpreterApplication() {}

    public static FSInterpreterApplication getInstance() {
        if (myself == null)
            myself = new FSInterpreterApplication();
        return myself;
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
