package ch.supsi.fscli.backend.application.filesystem.state;

import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSStateApplication implements IFSStateApplication {

    private final IFSStateBusiness fsBusiness;

    @Inject
    public FSStateApplication(IFSStateBusiness fsBusiness) {
        this.fsBusiness = fsBusiness;
    }

    @Override
    public boolean changeSavedStateAndGet() {
        return fsBusiness.changeSavedStateAndGet();
    }
}
