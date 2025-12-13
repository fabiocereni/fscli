package ch.supsi.fscli.backend.application.persistence;

import ch.supsi.fscli.backend.business.persistence.IFSDataReaderBusiness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FSDataReaderApplicationTest {

    @Mock
    private IFSDataReaderBusiness fsDataReaderBusiness;

    @InjectMocks
    private FSDataReaderApplication application;

    @Test
    void reader_delegatesToBusiness() {
        File file = new File("test.json");

        application.reader(file);

        verify(fsDataReaderBusiness).reader(file);
    }
}
