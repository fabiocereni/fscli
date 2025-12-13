package ch.supsi.fscli.backend.application.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.backend.business.filesystem.interpreter.IFSInterpreter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FSInterpreterApplicationTest {

    @Mock
    private IFSInterpreter interpreter;

    @InjectMocks
    private FSInterpreterApplication application;

//    @Test
//    void getCurrentpathDelegation() {
//        String path = "/home";
//        when(interpreter.getCurrentpath()).thenReturn(path);
//
//        String result = application.getCurrentpath();
//
//        assertSame(path, result);
//        verify(interpreter).getCurrentpath();
//    }

    @Test
    void executeDelegation() {
        String command = "ls";
        CommandResult commandResult = new CommandResult("output", false);
        when(interpreter.execute(command)).thenReturn(commandResult);

        CommandResult result = application.execute(command);

        assertSame(commandResult, result);
        verify(interpreter).execute(command);
    }
}
