package ch.supsi.fscli.frontend.controller.menubar;


import ch.supsi.fscli.frontend.controller.EventHandler;

public interface IQuitController extends EventHandler {
    boolean manageQuit();
    void confirmQuit();
}
