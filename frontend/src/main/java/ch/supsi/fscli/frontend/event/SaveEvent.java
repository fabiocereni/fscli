package ch.supsi.fscli.frontend.event;

public class SaveEvent extends AbstractEvent {
    public SaveEvent(Object source, String propertyName, Object oldValue, Object newValue) {
        super(source, propertyName, oldValue, newValue);
    }
}
