package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.FSStateBusiness;
import ch.supsi.fscli.backend.business.IFSStateBusiness;

public class FSStateApplication implements IFSStateApplication {

    private final IFSStateBusiness fsBusiness = FSStateBusiness.getInstance();

    private static FSStateApplication myself;

    private FSStateApplication() {}

    public static FSStateApplication getInstance() {
        if(myself == null)
            myself = new FSStateApplication();

        return myself;
    }

    @Override
    public boolean changeSavedStateAndGet() {
        return this.fsBusiness.changeSavedStateAndGet();
    }
}
