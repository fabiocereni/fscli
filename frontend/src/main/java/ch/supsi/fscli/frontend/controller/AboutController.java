package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.AboutViewQualifier;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AboutController implements IAboutView, IAboutController {

    private final IShow aboutView;

    @Inject
    public AboutController(@AboutViewQualifier IShow aboutView) {
        this.aboutView = aboutView;
    }

    @Override
    public void showAboutView() { aboutView.showMyView(); }

}
