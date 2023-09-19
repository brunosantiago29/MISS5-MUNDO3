import java.io.IOException;
import javax.swing.*;
import java.io.ObjectInputStream;
import javax.swing.SwingUtilities;
import java.util.List;

public class ThreadClient extends Thread {
    private final ObjectInputStream entrada;
    private final JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object objeto = entrada.readObject();

                SwingUtilities.invokeLater(() -> {
                    if (objeto instanceof String string) {
                        textArea.append(string + "\n");
                    } else if (objeto instanceof List) {
                        List<Produto> produtos;
                        produtos = (List<Produto>) objeto;
                        for (Produto produto : produtos) {
                            textArea.append("Nome: " + produto.getNome() + ", Quantidade: " + produto.getQuantidade() + "\n");
                        }
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    private static class Produto {

        public Produto() {
        }

        private String getNome() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getQuantidade() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
