package ch.supsi.fscli.frontend.event;

public class FilesystemCreatedEvent extends AbstractEvent {

    public FilesystemCreatedEvent(Object source, String propertyName, Object oldValue, Object newValue) {
        super(source, propertyName, oldValue, newValue);
    }
}
