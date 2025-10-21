package ch.supsi.fscli.frontend.view;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import javax.swing.*;

public class HelpView implements IShow {

    private static HelpView myself;

    private HelpView() {}

    public static HelpView getInstance() {
        if (myself == null) {
            myself = new HelpView();
        }
        return myself;
    }

    @Override
    public void showMyView() {
        String commands = """
            Comandi disponibili nel terminale:
            - ls: Mostra il contenuto della directory corrente
            - cd [directory]: Cambia directory
            - mkdir [nome_cartella]: Crea una nuova cartella
            - rm [file|cartella]: Elimina file o cartelle
            - touch [nome_file]: Crea un nuovo file vuoto
            - pwd: Mostra la directory corrente
            - exit: Chiude il terminale
            """;

        TextArea textArea = new TextArea(commands);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guida ai Comandi del Terminale");
        alert.setHeaderText("Di seguito trovi l'elenco dei comandi del terminale:");
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);

        alert.showAndWait();

    }
}
