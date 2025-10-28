package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;

public class HelpController implements IHelpController {

    private static HelpController myself;
    private IShow helpView;

    private HelpController() {
    }

    public static HelpController getInstance() {
        if(myself == null)
            myself = new HelpController();
        return myself;
    }

    @Override
    public void showHelpView() {
        helpView.showMyView();
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        helpView = eventHandlerInitializer.helpView();
    }
}
