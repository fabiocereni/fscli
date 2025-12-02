package ch.supsi.fscli.frontend.controller.filesystem;

import ch.supsi.fscli.backend.application.filesystem.interpreter.IFSInterpreterApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CommandLineController implements ICommandLineController {

    private final IFSInterpreterApplication interpreterApplication;

    @Inject
    public CommandLineController(IFSInterpreterApplication interpreterApplication) {
        this.interpreterApplication = interpreterApplication;
    }

    @Override
    public String getCurrentPath() {
        return interpreterApplication.getCurrentpath();
    }

    @Override
    public String executeCommand(String commandLine) {
        return interpreterApplication.execute(commandLine);
    }
}
