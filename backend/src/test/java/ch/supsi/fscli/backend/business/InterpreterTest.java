package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.*;
import ch.supsi.fscli.backend.business.filesystem.interpreter.FSInterpreter;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    @Mock private FileSystem fileSystem;
    @Mock private CdCommand cd;
    @Mock private HelpCommand help;
    @Mock private LnCommand ln;
    @Mock private LsCommand ls;
    @Mock private MkdirCommand mkdir;
    @Mock private MvCommand mv;
    @Mock private PwdCommand pwd;
    @Mock private RmdirCommand rmdir;
    @Mock private RmfileCommand rmfile;
    @Mock private TouchCommand touch;
    @Mock private ClearCommand clear;

    private FSInterpreter fsInterpreter;

    @BeforeEach
    public void setup() {
        when(ls.getCommandName()).thenReturn("ls");
        when(cd.getCommandName()).thenReturn("cd");
        when(help.getCommandName()).thenReturn("help");
        when(ln.getCommandName()).thenReturn("ln");
        when(mkdir.getCommandName()).thenReturn("mkdir");
        when(mv.getCommandName()).thenReturn("mv");
        when(pwd.getCommandName()).thenReturn("pwd");
        when(rmdir.getCommandName()).thenReturn("rmdir");
        when(rmfile.getCommandName()).thenReturn("rmfile");
        when(touch.getCommandName()).thenReturn("touch");
        when(clear.getCommandName()).thenReturn("clear");

        fsInterpreter = new FSInterpreter(fileSystem, cd, help, ln, ls, mkdir, mv, pwd, rmdir, rmfile, touch, clear);
    }

    @Test
    void execute_returnsNullIfCommandLineIsNull() {
        CommandResult result = fsInterpreter.execute(null);
        assertNull(result);
    }

    @Test
    void execute_returnsNullIfCommandLineIsBlank() {
        CommandResult result = fsInterpreter.execute("   ");
        assertNull(result);
    }

    @Test
    void execute_returnsCommandNotFoundIfUnknownCommand() {
        CommandResult result = fsInterpreter.execute("unknown");
        assertEquals("label.commandNotFound", result.getContent());
        assertTrue(result.isTranslatable());
    }
}
