package ch.supsi.fscli.frontend.view;

public record EventHandlerInitializer(IShow savingView,
                                      IQuitView quitView,
                                      IShow helpView,
                                      IShow aboutView) {
}
