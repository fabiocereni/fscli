package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.HelpViewQualifier;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HelpController implements IHelpController {

    @HelpViewQualifier
    @Inject
    private IShow helpView;

//    @Inject
//    public HelpController(@HelpViewQualifier IShow helpView) {
//        this.helpView = helpView;
//    }

    @Override
    public void showHelpView() {
        helpView.showMyView();
    }

}
