package ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSPwdCommandBusiness.class)
public interface IFSPwdCommandBusiness {
    String pwd();
}
