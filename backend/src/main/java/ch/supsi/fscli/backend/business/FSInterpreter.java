package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.business.FSCommands.*;

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

    private final Map<String, Function<List<String>, String>> commands;

    private FSInterpreter() {
        this.commands = new HashMap<>();
        this.commands.put("cd", this::handleCd);
        this.commands.put("mkdir", this::handleMkdir);
        this.commands.put("pwd", this::handlePwd);
        this.commands.put("rmdir", this::handleRm);
        this.commands.put("rm", this::handleRmFile);
        this.commands.put("touch", this::handleTouch);
    }

    public static FSInterpreter getInstance() {
        if(myself == null)
            myself = new FSInterpreter();
        return myself;
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
            return "cd: comando non trovato.";
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

    private String handleRm(List<String> args) {
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
            return "pwd: il comando non accetta argomenti.";
        }
        return pwdCommandBusiness.pwd();
    }

    private String handleTouch(List<String> args) {
        if (args.size() == 1) {
            boolean success = touchCommandBusiness.touch(args.get(0), null);
            return success ? null : "touch: impossibile creare il file.";

        } else if (args.size() == 2) {
            boolean success = touchCommandBusiness.touch(args.get(0), args.get(1));
            return success ? null : "touch: impossibile creare il file (percorso non valido?).";
        } else {
            return "touch: numero di argomenti errato. (uso: touch <nome_file> [percorso_destinazione])";
        }
    }


}
