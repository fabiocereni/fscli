package ch.supsi.fscli.frontend.view;

import java.awt.*;

public record EventHandlerInitializer(IShow savingView,
                                      IQuitView quitView,
                                      IShow helpView,
                                      IShow aboutView) {
}
