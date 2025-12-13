package ch.supsi.fscli.frontend.controller.filesystem;

import ch.supsi.fscli.backend.application.filesystem.interpreter.IFSInterpreterApplication;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
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
    public CommandResult executeCommand(String commandLine) {
        return interpreterApplication.execute(commandLine);
    }
}