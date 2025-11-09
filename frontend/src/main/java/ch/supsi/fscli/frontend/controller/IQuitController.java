package ch.supsi.fscli.frontend.controller;


public interface IQuitController extends EventHandler {
    boolean manageQuit();
    void confirmQuit();
}
