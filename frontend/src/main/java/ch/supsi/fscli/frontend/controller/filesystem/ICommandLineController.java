package ch.supsi.fscli.frontend.controller.filesystem;

public interface ICommandLineController {

    String getCurrentPath();
    String executeCommand(String commandLine);

}
