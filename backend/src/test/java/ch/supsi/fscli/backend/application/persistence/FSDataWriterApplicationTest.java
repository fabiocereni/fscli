package ch.supsi.fscli.backend.application.persistence;

import ch.supsi.fscli.backend.business.persistence.IFSDataWriterBusiness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FSDataWriterApplicationTest {

    @Mock
    private IFSDataWriterBusiness fsDataWriterBusiness;

    @InjectMocks
    private FSDataWriterApplication application;

    @Test
    void saveWithPath_delegatesToBusiness() {
        Path path = Path.of("test.json");

        application.save(path);

        verify(fsDataWriterBusiness).save(path);
    }

    @Test
    void saveWithoutPath_delegatesToBusiness() {
        application.save();

        verify(fsDataWriterBusiness).save();
    }
}
