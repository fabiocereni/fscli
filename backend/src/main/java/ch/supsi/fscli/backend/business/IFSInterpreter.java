package ch.supsi.fscli.backend.business;

public interface IFSInterpreter {

    String getCurrentpath();
    String execute(String commandLine);

}
