package ch.supsi.fscli.backend.business.FSCommands;

import ch.supsi.fscli.backend.business.*;

import java.util.Optional;

public class FSTouchCommandBusiness implements IFSTouchCommandBusiness {

    private static FSTouchCommandBusiness myself;

    private final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    private FSTouchCommandBusiness() {}

    public static FSTouchCommandBusiness getInstance() {
        if(myself == null)
            myself = new FSTouchCommandBusiness();

        return myself;
    }


    @Override
    public boolean touch(String fileName, String path) {

        if(path == null) {
            this.ifsStateBusiness.getCurrentWorkingDirectory()
                    .addContent(new FileBusiness(this.ifsStateBusiness.getCurrentWorkingDirectory(), fileName));
            return true;
        }
        return false;
    }
}
