package ch.supsi.fscli.backend.business.FSCommands.pwd;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSPwdCommandBusiness.class)
public interface IFSPwdCommandBusiness {
    String pwd();
}
