package ch.supsi.fscli.backend.business.FSCommands.pwd;

import ch.supsi.fscli.backend.business.FSStateBusiness;
import ch.supsi.fscli.backend.business.IDirectoryBusiness;
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
        IDirectoryBusiness currentDir = this.ifsStateBusiness.getCurrentWorkingDirectory();
        StringBuilder sb = new StringBuilder();

        while (currentDir != null && !"root".equals(currentDir.getName())) {
            sb.insert(0, "/" + currentDir.getName());
            currentDir = currentDir.getParent();
        }

        if (sb.isEmpty()) {
            return "/";
        }

        return sb.toString();
    }

}
