package ch.supsi.fscli.frontend.model.filesystem;

import ch.supsi.fscli.backend.application.filesystem.interpreter.IFSInterpreterApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Arrays;

@Singleton
public class CommandLineModel implements ICommandLineModel {

    private final IFSInterpreterApplication interpreterApplication;

    @Inject
    public CommandLineModel(IFSInterpreterApplication interpreterApplication) {
        this.interpreterApplication = interpreterApplication;
    }

    @Override
    public String getCurrentPath() {
        return interpreterApplication.getCurrentpath();
    }

    @Override
    public String executeCommand(String commandLine) {
        System.out.println("Executing command: " + commandLine);

        if (commandLine == null || commandLine.isBlank())
            return null;

        String[] tmp = commandLine.split(" ");


        boolean hasFlag = tmp.length > 1 && tmp[1].startsWith("-");
        int startIndex = hasFlag ? 2 : 1;

        StringBuilder base = new StringBuilder();


        for (int i = 0; i < startIndex && i < tmp.length; i++) {
            base.append(tmp[i]).append(" ");
        }

        int newLength = tmp.length - startIndex;
        StringBuilder stringBuilder = new StringBuilder();
        String baseCmd = base.toString().trim();

        if (newLength > 0) {
            String[] tokens = new String[newLength];
            System.arraycopy(tmp, startIndex, tokens, 0, newLength);

            for (String c : tokens) {
                String fullCommand = baseCmd + " " + c;

                String result = interpreterApplication.execute(fullCommand);
                if (result != null) {
                    stringBuilder.append(result).append("\n");
                }
            }
        } else {
            String result = interpreterApplication.execute(baseCmd);
            if (result != null) {
                stringBuilder.append(result).append("\n");
            }
        }

        System.out.println(stringBuilder);

        return stringBuilder.toString();
    }
}
