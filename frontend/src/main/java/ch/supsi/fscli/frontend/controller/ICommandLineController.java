package ch.supsi.fscli.frontend.controller;

public interface ICommandLineController {

    String getCurrentPath();
    String executeCommand(String commandLine);

}
