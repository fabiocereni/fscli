package ch.supsi.fscli.frontend.event;

public class LoadFSEvent extends AbstractEvent {
    public LoadFSEvent(Object source, String propertyName, Object oldValue, Object newValue) {
        super(source, propertyName, oldValue, newValue);
    }
}
