package ch.supsi.fscli.backend.application;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSInterpreterApplication.class)
public interface IFSInterpreterApplication {

    String getCurrentpath();
    String execute(String commandLine);

}
