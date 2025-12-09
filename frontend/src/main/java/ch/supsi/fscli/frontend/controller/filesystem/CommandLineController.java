package ch.supsi.fscli.frontend.controller.filesystem;

import ch.supsi.fscli.frontend.model.filesystem.ICommandLineModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CommandLineController implements ICommandLineController {

    private final ICommandLineModel iCommandLineModel;

    @Inject
    public CommandLineController(ICommandLineModel iCommandLineModel) {
        this.iCommandLineModel = iCommandLineModel;
    }

    @Override
    public String getCurrentPath() {
        return iCommandLineModel.getCurrentPath();
    }

    @Override
    public String executeCommand(String commandLine) {
        return iCommandLineModel.executeCommand(commandLine);
    }
}
