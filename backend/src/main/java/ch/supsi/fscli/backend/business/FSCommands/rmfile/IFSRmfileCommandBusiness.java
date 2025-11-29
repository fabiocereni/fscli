package ch.supsi.fscli.backend.business.FSCommands.rmfile;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSRmfilecommandBusiness.class)
public interface IFSRmfileCommandBusiness {

    boolean rmfile(String name);

}
