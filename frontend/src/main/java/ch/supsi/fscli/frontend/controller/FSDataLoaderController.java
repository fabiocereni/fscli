package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.LoadingViewQualifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.nio.file.Path;

@Singleton
public class FSDataLoaderController implements IFSDataLoaderController {


    @Inject
    @LoadingViewQualifier
    private IShow loadingView;

    @Override
    public void load(Path path) {

    }

    @Override
    public void showLoadingView() {
        this.loadingView.showMyView();
    }
}
