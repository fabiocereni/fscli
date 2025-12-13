package ch.supsi.fscli.frontend.modules;


import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ViewModule());
        install(new ControllerModule());
        install(new DirectorModule());
        install(new ModelModule());
    }
}
