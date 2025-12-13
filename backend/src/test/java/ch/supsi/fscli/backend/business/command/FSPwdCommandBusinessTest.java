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

    // 1. Creiamo un MOCK dello stato (una versione finta)
    // Non usiamo quello vero, così non dobbiamo costruire alberi di directory
    @Mock
    private FileSystem stateMock;

    // 2. Iniettiamo il mock dentro la classe da testare
    // Mockito userà il costruttore annotato con @Inject automaticamente
    @InjectMocks
    private FSPwdCommandBusiness pwdCommand;

    @Test
    void testPwdDelegatesToState() {
        // --- ARRANGE (Preparazione) ---
        String expectedPath = "/home/user/documents";

        // Istruiamo il mock: "Quando qualcuno ti chiede il path, rispondi con questa stringa"
        when(stateMock.getCurrentWorkingDirectoryPath()).thenReturn(expectedPath);

        // --- ACT (Esecuzione) ---
        String result = pwdCommand.pwd();

        // --- ASSERT (Verifica) ---
        // 1. Verifichiamo che il risultato sia quello atteso
        assertEquals(expectedPath, result);

        // 2. (Opzionale ma consigliato) Verifichiamo che il metodo dello stato sia stato chiamato 1 volta sola
        verify(stateMock, times(1)).getCurrentWorkingDirectoryPath();
    }

    @Test
    void testPwdAtRoot() {
        // Testiamo il caso root
        when(stateMock.getCurrentWorkingDirectoryPath()).thenReturn("/");

        String result = pwdCommand.pwd();

        assertEquals("/", result);
    }
}