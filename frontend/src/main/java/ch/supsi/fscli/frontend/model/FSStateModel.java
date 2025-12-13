package ch.supsi.fscli.frontend.model;

import com.google.inject.Singleton;

@Singleton
public class FSStateModel implements IFSStateModel {
    private boolean closeable = true;

    @Override
    public boolean isCloseable() {
        return closeable;
    }

    @Override
    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }
}
