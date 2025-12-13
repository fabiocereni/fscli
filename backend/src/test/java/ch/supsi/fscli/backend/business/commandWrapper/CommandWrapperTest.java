package ch.supsi.fscli.backend.business.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.cd.IFSCdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.ln.IFSLnCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.IFSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.mkdir.IFSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.mv.IFSMvCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.pwd.IFSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmfile.IFSRmfileCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.FSCommands.touch.IFSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.commandWrapper.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandWrapperTest {

    @Mock
    private IFSCdCommandBusiness ifsCdCommandBusiness;
    @InjectMocks
    private CdCommand cdCommand;


    @Mock
    private IFSLnCommandBusiness ifsLnCommandBusiness;
    @InjectMocks
    private LnCommand lnCommand;


    @Mock
    private IFSLsCommandBusiness ifsLsCommandBusiness;
    @InjectMocks
    private LsCommand lsCommand;


    @Mock
    private IFSMkdirCommandBusiness ifsMkdirCommandBusiness;
    @InjectMocks
    private MkdirCommand mkdirCommand;


    @Mock
    private IFSMvCommandBusiness ifsMvCommandBusiness;
    @InjectMocks
    private MvCommand mvCommand;


    @Mock
    private IFSPwdCommandBusiness ifsPwdCommandBusiness;
    @InjectMocks
    private PwdCommand pwdCommand;


    @Mock
    private IFSRmdirCommandBusiness ifsRmdirCommandBusiness;
    @InjectMocks
    private RmdirCommand rmDirCommand;


    @Mock
    private IFSRmfileCommandBusiness ifsRmfileCommandBusiness;
    @InjectMocks
    private RmfileCommand rmFileCommand;


    @Mock
    private IFSTouchCommandBusiness ifsTouchCommandBusiness;
    @InjectMocks
    private TouchCommand touchCommand;



    @Test
    void cdCommandDelegation() {
        when(ifsCdCommandBusiness.cd("test")).thenReturn(true);

        cdCommand.execute(List.of("test"));
        verify(ifsCdCommandBusiness).cd("test");
    }


    @Test
    void lnCommandDelegation() {
        when(ifsLnCommandBusiness.ln("target", "linkname")).thenReturn("passed");

        lnCommand.execute(List.of("target", "linkname"));
        verify(ifsLnCommandBusiness).ln("target", "linkname");
    }



    @Test
    void lsCommandDelegation() {
        when(ifsLsCommandBusiness.ls("test", false))
                .thenReturn(new CommandResult("test",false));

        lsCommand.execute(List.of("test"));

        verify(ifsLsCommandBusiness).ls("test",false);
    }


    @Test
    void mkdirCommandDelegation() {

        when(ifsMkdirCommandBusiness.mkdir("dir")).thenReturn(true);

        mkdirCommand.execute(List.of("dir"));

        verify(ifsMkdirCommandBusiness).mkdir("dir");

    }


    @Test
    void moveCommandDelegation() {

        when(ifsMvCommandBusiness.mv("source", "destination"))
                .thenReturn(true);

        mvCommand.execute(List.of("source", "destination"));

        verify(ifsMvCommandBusiness).mv("source", "destination");
    }


    @Test
    void pwdCommandDelegation() {
        when(ifsPwdCommandBusiness.pwd()).thenReturn("correct path");
        pwdCommand.execute(List.of());
        verify(ifsPwdCommandBusiness).pwd();
    }


    @Test
    void rmdirCommandDelegation() {
        when(ifsRmdirCommandBusiness.rmdir("dir")).thenReturn(true);

        rmDirCommand.execute(List.of("dir"));

        verify(ifsRmdirCommandBusiness).rmdir("dir");
    }


    @Test
    void rmFileCommandDelegation() {

        when(ifsRmfileCommandBusiness.rmfile("file")).thenReturn(true);

        rmFileCommand.execute(List.of("file"));

        verify(ifsRmfileCommandBusiness).rmfile("file");
    }


    @Test
    void touchCommandDelegation() {

        when(ifsTouchCommandBusiness.touch("file")).thenReturn("true");

        touchCommand.execute(List.of("file"));

        verify(ifsTouchCommandBusiness).touch("file");
    }

}
