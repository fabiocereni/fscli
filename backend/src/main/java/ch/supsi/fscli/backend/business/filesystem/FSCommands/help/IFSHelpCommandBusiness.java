package ch.supsi.fscli.backend.business.filesystem.FSCommands.help;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSHelpCommandBusiness.class)
public interface IFSHelpCommandBusiness {
    boolean help();
}
