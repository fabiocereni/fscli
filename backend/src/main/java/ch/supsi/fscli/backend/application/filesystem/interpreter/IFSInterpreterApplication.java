package ch.supsi.fscli.backend.application.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import com.google.inject.ImplementedBy;

@ImplementedBy(FSInterpreterApplication.class)
public interface IFSInterpreterApplication {
    CommandResult execute(String commandLine);
}
