import java.io.*;
import java.net.*;
import javax.swing.JTextArea;

public class ClienteCadastro {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("op1"); // Envia login
            out.writeObject("op1"); // Envia senha

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            SaidaFrame saidaFrame = new SaidaFrame();
            ThreadClient threadClient = new ThreadClient(in, saidaFrame.getTextArea());
            threadClient.start();

            while (true) {
                System.out.println("Escolha uma opção:");
                System.out.println("L - Listar");
                System.out.println("X - Finalizar");
                System.out.println("E - Entrada");
                System.out.println("S - Saída");

                String comando = reader.readLine();

                if (comando.equalsIgnoreCase("L")) {
                    out.writeObject("L");
                } else if (comando.equalsIgnoreCase("X")) {
                    socket.close();
                    break;
                } else if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    System.out.println("Digite o Id da pessoa:");
                    String idPessoa = reader.readLine();
                    out.writeObject(idPessoa);

                    System.out.println("Digite o Id do produto:");
                    String idProduto = reader.readLine();
                    out.writeObject(idProduto);

                    System.out.println("Digite a quantidade:");
                    String quantidade = reader.readLine();
                    out.writeObject(quantidade);

                    System.out.println("Digite o valor unitário:");
                    String valorUnitario = reader.readLine();
                    out.writeObject(valorUnitario);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SaidaFrame {

        public SaidaFrame() {
        }

        private JTextArea getTextArea() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
