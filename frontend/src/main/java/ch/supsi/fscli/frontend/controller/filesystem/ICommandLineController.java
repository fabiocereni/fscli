package ch.supsi.fscli.frontend.controller.filesystem;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;

public interface ICommandLineController {

    String getCurrentPath();
    CommandResult executeCommand(String commandLine);

}
