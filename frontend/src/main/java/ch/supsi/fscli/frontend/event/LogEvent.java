package ch.supsi.fscli.frontend.event;

public class LogEvent extends AbstractEvent {
    private final String message;

    public LogEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}