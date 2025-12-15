package ch.supsi.fscli.backend.business.command;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.FSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FSPwdCommandBusinessTest {
    @Mock
    private FileSystem stateMock;

    @InjectMocks
    private FSPwdCommandBusiness pwdCommand;

    @Test
    void testPwdDelegatesToState() {
        String expectedPath = "/home/user/documents";

        when(stateMock.getCurrentWorkingDirectoryPath()).thenReturn(expectedPath);

        String result = pwdCommand.pwd();

        assertEquals(expectedPath, result);

        verify(stateMock, times(1)).getCurrentWorkingDirectoryPath();
    }

    @Test
    void testPwdAtRoot() {
        when(stateMock.getCurrentWorkingDirectoryPath()).thenReturn("/");

        String result = pwdCommand.pwd();

        assertEquals("/", result);
    }
}