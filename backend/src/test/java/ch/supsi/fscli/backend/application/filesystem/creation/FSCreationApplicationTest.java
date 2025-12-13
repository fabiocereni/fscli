package ch.supsi.fscli.backend.application.filesystem.creation;


import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FSCreationApplicationTest {

    @Mock
    private IFSCreationBusiness fsCreationBusiness;

    @InjectMocks
    private FSCreationApplication fsCreationApplication;

    @Test
    void createFileSystem_delegatesToBusiness() {
        fsCreationApplication.createFileSystem();
        verify(fsCreationBusiness).newfs();

    }
}
