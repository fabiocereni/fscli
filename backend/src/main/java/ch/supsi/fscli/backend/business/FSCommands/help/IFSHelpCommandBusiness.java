package ch.supsi.fscli.backend.business.FSCommands.help;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSHelpCommandBusiness.class)
public interface IFSHelpCommandBusiness {
    boolean help();
}
