package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.EventHandlerInitializer;

public interface EventHandler {
    void initialize(EventHandlerInitializer eventHandlerInitializer);
}
