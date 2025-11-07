package ch.supsi.fscli.frontend.view;

import com.google.inject.Singleton;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

@Singleton
public class HelpView implements IShow {

    @Override
    public void showMyView() {
        String commands = """
            ================================================================
            GUIDA AI COMANDI DEL TERMINALE (Subset UNIX semplificato)
            ================================================================

            pwd
                Stampa la directory di lavoro corrente (Print Working Directory).
            
            touch FILE...
                Crea uno o più file di testo vuoti.

            mkdir DIRECTORY...
                Crea una o più nuove directory (cartelle).

            cd [DIRECTORY]
                Cambia la directory corrente. Se [DIRECTORY] non è specificata,
                il comportamento tipico è tornare alla home directory.

            rm FILE...
                Rimuove (elimina) i file specificati.

            rmdir DIRECTORY...
                Rimuove (elimina) le directory specificate. Funziona solo se le directory sono vuote.

            mv SOURCE DESTINATION
                Sposta un file/directory (SOURCE) in una nuova posizione/nome (DESTINATION).
                Agisce anche come comando di ridenominazione (rename).

            ln [-s] TARGET LINK_NAME
                Crea un link (collegamento).
                - Senza -s: Crea un hard link (non supportato per le directory).
                - Con -s: Crea un soft link (link simbolico) a TARGET.

            ls [-i] FILE...
                Elenca il contenuto della directory.
                - Opzione -i: Mostra l'inode number (numero di indice) di ogni elemento.

            clear
                Pulisce l'area di output del terminale.

            help
                Mostra questo elenco dei comandi disponibili e la loro sintassi.
            
            exit
                Chiude l'applicazione terminale.
            """;

        TextArea textArea = new TextArea(commands);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(600, 400);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guida ai Comandi del Terminale (Help)");
        alert.setHeaderText("Elenco completo dei comandi supportati:");
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);

        alert.showAndWait();

    }
}