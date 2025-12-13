package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.IFSCommand;
import ch.supsi.fscli.backend.business.filesystem.interpreter.FSInterpreter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    @Mock
    private IFSPwdCommandBusiness pwdCommandBusiness;

    @Mock
    private IFSCommand command;

    @Test
    void getCurrentpathDelegation() {
        when(pwdCommandBusiness.pwd()).thenReturn("/home");

        FSInterpreter interpreter = new FSInterpreter(Set.of());

        String result = interpreter.getCurrentpath();

        assertEquals("/home", result);
    }

    @Test
    void execute_returnsNullIfCommandLineIsNull() {
        FSInterpreter interpreter = new FSInterpreter(Set.of());

        CommandResult result = interpreter.execute(null);

        assertNull(result);
    }

    @Test
    void execute_returnsNullIfCommandLineIsBlank() {
        FSInterpreter interpreter = new FSInterpreter(Set.of());

        CommandResult result = interpreter.execute("   ");

        assertNull(result);
    }

    @Test
    void execute_returnsCommandNotFoundIfUnknownCommand() {
        FSInterpreter interpreter = new FSInterpreter(Set.of());

        CommandResult result = interpreter.execute("unknown");

        assertEquals("label.commandNotFound", result.getContent());
        assertTrue(result.isTranslatable());
    }

    @Test
    void execute_executesKnownCommand() {
        when(command.getCommandName()).thenReturn("ls");
        CommandResult commandResult = new CommandResult("ok", false);
        when(command.execute(List.of("a", "b"))).thenReturn(commandResult);

        FSInterpreter interpreter = new FSInterpreter(Set.of(command));

        CommandResult result = interpreter.execute("ls a b");

        assertEquals(commandResult, result);
    }
}
