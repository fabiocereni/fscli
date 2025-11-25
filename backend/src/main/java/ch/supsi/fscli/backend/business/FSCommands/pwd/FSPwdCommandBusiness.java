package ch.supsi.fscli.backend.business.FSCommands.pwd;

import ch.supsi.fscli.backend.business.FSStateBusiness;
import ch.supsi.fscli.backend.business.IFSStateBusiness;

public class FSPwdCommandBusiness implements IFSPwdCommandBusiness {

    private static FSPwdCommandBusiness myself;

    private final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    private FSPwdCommandBusiness() {}

    public static FSPwdCommandBusiness getInstance() {
        if(myself == null)
            myself = new FSPwdCommandBusiness();

        return myself;
    }

    @Override
    public String pwd() {
        return ifsStateBusiness.getCurrentWorkingDirectoryPath();
    }

}
