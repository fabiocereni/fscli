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

        cdCommand.setArgs(List.of("test"));
        cdCommand.execute();
        verify(ifsCdCommandBusiness).cd("test");
    }


    @Test
    void lnCommandDelegation() {
        when(ifsLnCommandBusiness.ln("target", "linkname")).thenReturn("passed");

        lnCommand.setArgs(List.of("target", "linkname"));
        lnCommand.execute();
        verify(ifsLnCommandBusiness).ln("target", "linkname");
    }



    @Test
    void lsCommandDelegation() {
        when(ifsLsCommandBusiness.ls("test", false))
                .thenReturn(new CommandResult("test",false));

        lsCommand.setArgs(List.of("test"));
        lsCommand.execute();

        verify(ifsLsCommandBusiness).ls("test",false);
    }


    @Test
    void mkdirCommandDelegation() {

        when(ifsMkdirCommandBusiness.mkdir("dir")).thenReturn(true);

        mkdirCommand.setArgs(List.of("dir"));
        mkdirCommand.execute();

        verify(ifsMkdirCommandBusiness).mkdir("dir");

    }


    @Test
    void moveCommandDelegation() {

        when(ifsMvCommandBusiness.mv("source", "destination"))
                .thenReturn(true);

        mvCommand.setArgs(List.of("source", "destination"));
        mvCommand.execute();

        verify(ifsMvCommandBusiness).mv("source", "destination");
    }


    @Test
    void pwdCommandDelegation() {
        when(ifsPwdCommandBusiness.pwd()).thenReturn("correct path");
        pwdCommand.setArgs(List.of());
        pwdCommand.execute();
        verify(ifsPwdCommandBusiness).pwd();
    }


    @Test
    void rmdirCommandDelegation() {
        when(ifsRmdirCommandBusiness.rmdir("dir")).thenReturn(true);

        rmDirCommand.setArgs(List.of("dir"));
        rmDirCommand.execute();

        verify(ifsRmdirCommandBusiness).rmdir("dir");
    }


    @Test
    void rmFileCommandDelegation() {

        when(ifsRmfileCommandBusiness.rmfile("file")).thenReturn(true);

        rmFileCommand.setArgs(List.of("file"));
        rmFileCommand.execute();

        verify(ifsRmfileCommandBusiness).rmfile("file");
    }


    @Test
    void touchCommandDelegation() {

        when(ifsTouchCommandBusiness.touch("file")).thenReturn("true");

        touchCommand.setArgs(List.of("file"));
        touchCommand.execute();

        verify(ifsTouchCommandBusiness).touch("file");
    }

}
