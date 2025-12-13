//package ch.supsi.fscli.backend.business;
//
//import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
//import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
//import ch.supsi.fscli.backend.business.filesystem.commandWrapper.IFSCommand;
//import ch.supsi.fscli.backend.business.filesystem.interpreter.FSInterpreter;
//import ch.supsi.fscli.backend.business.filesystem.interpreter.IFSInterpreter;
//import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
//import com.google.inject.Inject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class InterpreterTest {
//
//    @Mock
//    private IFSPwdCommandBusiness pwdCommandBusiness;
//
//    @Mock
//    private IFSCommand command;
//
//    @Inject
//    private IFSInterpreter ifsInterpreter;
//
//    @BeforeEach
//    public void setup() {
//
//        ifsInterpreter = new FSInterpreter();
//
//    }
//
////    @Test
////    void getCurrentpathDelegation() {
////        when(pwdCommandBusiness.pwd()).thenReturn("/home");
////
////
////        String result = ifsInterpreter.getCurrentpath();
////
////        assertEquals("/home", result);
////    }
//
//    @Test
//    void execute_returnsNullIfCommandLineIsNull() {
//
//
//        CommandResult result = ifsInterpreter.execute(null);
//
//        assertNull(result);
//    }
//
//    @Test
//    void execute_returnsNullIfCommandLineIsBlank() {
//
//        CommandResult result = ifsInterpreter.execute("   ");
//
//        assertNull(result);
//    }
//
//    @Test
//    void execute_returnsCommandNotFoundIfUnknownCommand() {
//
//        CommandResult result = ifsInterpreter.execute("unknown");
//
//        assertEquals("label.commandNotFound", result.getContent());
//        assertTrue(result.isTranslatable());
//    }
//
//    @Test
//    void execute_executesKnownCommand() {
//        when(command.getCommandName()).thenReturn("ls");
//        CommandResult commandResult = new CommandResult("ok", false);
//        command.setArgs(List.of("a", "b"));
//        when(command.execute()).thenReturn(commandResult);
//
//
//
//        CommandResult result = ifsInterpreter.execute("ls a b");
//
//        assertEquals(commandResult, result);
//    }
//}
