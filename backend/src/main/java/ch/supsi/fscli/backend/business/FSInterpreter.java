package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.cd.FSCdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.cd.IFSCdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.help.FSHelpCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.help.IFSHelpCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.mkdir.FSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.mkdir.IFSMkdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.mv.FSMvCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.mv.IFSMvCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.pwd.FSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.pwd.IFSPwdCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmdir.FSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmdir.IFSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmfile.FSRmfilecommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.rmfile.IFSRmfileCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.touch.FSTouchCommandBusiness;
import ch.supsi.fscli.backend.business.FSCommands.touch.IFSTouchCommandBusiness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FSInterpreter implements IFSInterpreter {

    private static FSInterpreter myself;

    private final IFSCdCommandBusiness cdCommandBusiness = FSCdCommandBusiness.getInstance();
    private final IFSMkdirCommandBusiness mkdirCommandBusiness = FSMkdirCommandBusiness.getInstance();
    private final IFSPwdCommandBusiness pwdCommandBusiness = FSPwdCommandBusiness.getInstance();
    private final IFSRmdirCommandBusiness rmdirCommandBusiness = FSRmdirCommandBusiness.getInstance();
    private final IFSRmfileCommandBusiness rmfileCommandBusiness = FSRmfilecommandBusiness.getInstance();
    private final IFSTouchCommandBusiness touchCommandBusiness = FSTouchCommandBusiness.getInstance();
    //private final IFSLsCommandBusiness lssCommandBusiness = IFSLsCommandBusiness.getInstance();
    private final IFSMvCommandBusiness mvCommandBusiness = FSMvCommandBusiness.getInstance();
    private final IFSHelpCommandBusiness helpCommandBusiness = FSHelpCommandBusiness.getInstance();

    private final Map<String, Function<List<String>, String>> commands;

    private FSInterpreter() {
        this.commands = new HashMap<>();
        this.commands.put("cd", this::handleCd);
        this.commands.put("mkdir", this::handleMkdir);
        this.commands.put("pwd", this::handlePwd);
        this.commands.put("rmdir", this::handleRmDir);
        this.commands.put("rm", this::handleRmFile);
        this.commands.put("touch", this::handleTouch);
        this.commands.put("clear", this::handleClear);
        //this.commands.put("ls", this::handleLs);
        this.commands.put("mv", this::handleMv);
        this.commands.put("help", this::handleHelp);
    }

    public static FSInterpreter getInstance() {
        if(myself == null)
            myself = new FSInterpreter();
        return myself;
    }

    @Override
    public String getCurrentpath() {
        return pwdCommandBusiness.pwd();
    }

    @Override
    public String execute(String commandLine) {
        if (commandLine == null || commandLine.isBlank())
            return null;

        // faccio il parsing qui per dividere comando e argomenti
        String[] tokens = commandLine.trim().split("\\s+");
        String command = tokens[0].toLowerCase();
        List<String> args = List.of(tokens).subList(1, tokens.length);

        Function<List<String>, String> handler = commands.get(command);
        if (handler == null) {
            return "comando non trovato.";
        } else {
            return handler.apply(args);
        }
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
        if (!args.isEmpty()) {
            return null;
        }
        return pwdCommandBusiness.pwd();
    }

    private String handleTouch(List<String> args) {
        if(args.size() != 1) {
            return "touch: numero di argomenti errato - (uso: touch <nome_file>)";
        }

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

//    private String handleLs(List<String> args) {
//        String path = null;
//        boolean flagI = false;
//
//        for (String arg : args) {
//            if (arg.equals("-i")) flagI = true;
//            else path = arg; // primo argomento che non è flag → percorso
//        }
//
//        String result = lssCommandBusiness.ls(path,  flagI);
//
//        if (result.isEmpty())
//            return "ls: errore comando";
//        return result;
//    }

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

}
