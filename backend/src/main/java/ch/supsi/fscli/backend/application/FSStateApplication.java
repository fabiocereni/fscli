package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.FSStateBusiness;
import ch.supsi.fscli.backend.business.IFSStateBusiness;
import com.google.inject.Singleton;

@Singleton
public class FSStateApplication implements IFSStateApplication {

    private final IFSStateBusiness fsBusiness = FSStateBusiness.getInstance();

    @Override
    public boolean changeSavedStateAndGet() {
        return this.fsBusiness.changeSavedStateAndGet();
    }
}
