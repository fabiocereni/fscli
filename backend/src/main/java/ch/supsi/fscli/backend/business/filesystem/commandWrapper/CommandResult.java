package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

public class CommandResult {
    private final String content;
    private final boolean isTranslatable;

    public CommandResult(String content, boolean isTranslatable) {
        this.content = content;
        this.isTranslatable = isTranslatable;
    }

    public String getContent() {
        return content;
    }

    public boolean isTranslatable() {
        return isTranslatable;
    }
}