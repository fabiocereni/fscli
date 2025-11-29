package ch.supsi.fscli.backend.business.FSCommands.pwd;

import ch.supsi.fscli.backend.business.IFSStateBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSPwdCommandBusiness implements IFSPwdCommandBusiness {

    private final IFSStateBusiness ifsStateBusiness;

    @Inject
    public FSPwdCommandBusiness(IFSStateBusiness ifsStateBusiness) {
        this.ifsStateBusiness = ifsStateBusiness;
    }

    @Override
    public String pwd() {
        return ifsStateBusiness.getCurrentWorkingDirectoryPath();
    }
}
