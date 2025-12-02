package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.cd.IFSCdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.help.IFSHelpCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.ls.IFSLsCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.mkdir.IFSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.mv.IFSMvCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.pwd.IFSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmdir.IFSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmfile.IFSRmfileCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.touch.IFSTouchCommandBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class FSInterpreter implements IFSInterpreter {

    private final IFSCdCommandBusiness cdCommandBusiness;
    private final IFSMkdirCommandBusiness mkdirCommandBusiness;
    private final IFSPwdCommandBusiness pwdCommandBusiness;
    private final IFSRmdirCommandBusiness rmdirCommandBusiness;
    private final IFSRmfileCommandBusiness rmfileCommandBusiness;
    private final IFSTouchCommandBusiness touchCommandBusiness;
    private final IFSMvCommandBusiness mvCommandBusiness;
    private final IFSLsCommandBusiness lsCommandBusiness;

    private final Map<String, Function<List<String>, String>> commands;

    @Inject
    protected FSInterpreter(IFSCdCommandBusiness cdCommandBusiness,
                            IFSMkdirCommandBusiness mkdirCommandBusiness,
                            IFSPwdCommandBusiness pwdCommandBusiness,
                            IFSRmdirCommandBusiness rmdirCommandBusiness,
                            IFSRmfileCommandBusiness rmfileCommandBusiness,
                            IFSTouchCommandBusiness touchCommandBusiness,
                            IFSMvCommandBusiness mvCommandBusiness,
                            IFSLsCommandBusiness lsCommandBusiness) {

        this.cdCommandBusiness = cdCommandBusiness;
        this.mkdirCommandBusiness = mkdirCommandBusiness;
        this.pwdCommandBusiness = pwdCommandBusiness;
        this.rmdirCommandBusiness = rmdirCommandBusiness;
        this.rmfileCommandBusiness = rmfileCommandBusiness;
        this.touchCommandBusiness = touchCommandBusiness;
        this.mvCommandBusiness = mvCommandBusiness;
        this.lsCommandBusiness = lsCommandBusiness;

        this.commands = new HashMap<>();
        this.commands.put("cd", this::handleCd);
        this.commands.put("mkdir", this::handleMkdir);
        this.commands.put("pwd", this::handlePwd);
        this.commands.put("rmdir", this::handleRmDir);
        this.commands.put("rm", this::handleRmFile);
        this.commands.put("touch", this::handleTouch);
        this.commands.put("clear", this::handleClear);
        this.commands.put("mv", this::handleMv);
        this.commands.put("help", this::handleHelp);
        this.commands.put("ls", this::handleLs);

    }

    @Override
    public String getCurrentpath() {
        return pwdCommandBusiness.pwd();
    }

    @Override
    public String execute(String commandLine) {
        if (commandLine == null || commandLine.isBlank())
            return null;

        String[] tokens = commandLine.trim().split("\\s+");
        String command = tokens[0].toLowerCase();
        List<String> args = List.of(tokens).subList(1, tokens.length);

        Function<List<String>, String> handler = commands.get(command);
        if (handler == null) {
            return "comando non trovato.";
        }
        return handler.apply(args);
    }

    private String handleCd(List<String> args){
        if (args.size() != 1) return "cd: numero di argomenti errato - (uso: cd <percorso>)";
        if (!cdCommandBusiness.cd(args.get(0))) {
            return "cd: percorso non trovato o non è una directory.";
        }
        return null;
    }

    private String handleMkdir(List<String> args) {
        if (args.size() != 1) return "mkdir: numero di argomenti errato - (uso: mkdir <nome>)";
        if (!mkdirCommandBusiness.mkdir(args.get(0))) {
            return "mkdir: impossibile creare la directory (potrebbe esistere già).";
        }
        return null;
    }

    private String handleRmDir(List<String> args) {
        if (args.size() != 1) return "rm: numero di argomenti errato - (uso: rm <nome_file>)";
        if (!rmdirCommandBusiness.rmdir(args.get(0))) {
            return "rm: impossibile rimuovere (non trovato o è una directory).";
        }
        return null;
    }

    private String handleRmFile(List<String> args) {
        if (args.size() != 1) return "rmfile: numero di argomenti errato - (uso: rmfile <nome_file>)";
        if (!rmfileCommandBusiness.rmfile(args.get(0))) {
            return "rmfile: impossibile rimuovere il file (non trovato o è una directory).";
        }
        return null;
    }

    private String handlePwd(List<String> args) {
        if (!args.isEmpty()) return null;
        return pwdCommandBusiness.pwd();
    }

    private String handleTouch(List<String> args) {
        if(args.size() != 1)
            return "touch: numero di argomenti errato - (uso: touch <nome_file>)";

        if(!touchCommandBusiness.touch(args.get(0)))
            return "touch: impossibile creare il file.";

        return null;
    }

    private String handleClear(List<String> args) {
        if (!args.isEmpty()) {
            return "clear: il comando non accetta argomenti.";
        }
        return "clear";
    }

    private String handleMv(List<String> args) {
        if (args.size() != 2)
            return "mv: numero di argomenti errato - (uso: mv <nome_file> <percorso_destinazione>)";

        if (!mvCommandBusiness.mv(args.get(0), args.get(1))) {
            return "mv: impossibile spostare il file o rinominarlo";
        }
        return null;
    }

    private String handleHelp(List<String> args) {
        if (args.isEmpty())
            return "label.infoHelp";
        return "help: numero di argomenti errato - (uso: help)";
    }

    private String handleLs(List<String> args) {
        // Parsing manuale per supportare "ls -i path", "ls path -i", o solo "ls -i"
        boolean showInode = false;
        String path = null;

        List<String> cleanArgs = new ArrayList<>();

        for (String arg : args) {
            if (arg.equals("-i")) {
                showInode = true;
            } else {
                cleanArgs.add(arg);
            }
        }

        if (cleanArgs.size() > 1) {
            return "ls: too many arguments";
        }

        if (!cleanArgs.isEmpty()) {
            path = cleanArgs.get(0);
        }

        return lsCommandBusiness.ls(path, showInode);
    }
}
