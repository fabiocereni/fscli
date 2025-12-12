package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import java.util.List;

public interface IFSCommand {
    // restituisce il nome del comando
    String getCommandName();

    void setArgs(List<String> args);

    // esegue la logica del comando
    CommandResult execute();

}
