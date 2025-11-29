package ch.supsi.fscli.backend.business.FSCommands.cd;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSCdCommandBusiness.class)
public interface IFSCdCommandBusiness {
    boolean cd(String name);
}
