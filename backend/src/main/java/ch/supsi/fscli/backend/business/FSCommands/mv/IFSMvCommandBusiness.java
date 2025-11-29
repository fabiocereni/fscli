package ch.supsi.fscli.backend.business.FSCommands.mv;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSMvCommandBusiness.class)
public interface IFSMvCommandBusiness {
    boolean mv(String source, String destination);
}
