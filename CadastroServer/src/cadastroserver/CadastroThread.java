import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import model.Produtos;

public class CadastroThread extends Thread {
    private final Socket s1;
    private final ProdutoJpaController ctrl;
    private final UsuarioJpaController ctrlUsu;

    /**
     *
     * @param s1
     * @param ctrl
     * @param ctrlUsu
     */
    public CadastroThread(Socket s1, ProdutoJpaController ctrl, UsuarioJpaController ctrlUsu) {
        this.s1 = s1;
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());

            // Obter login e senha do cliente
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            // Verificar as credenciais com o controlador de usuários
            var usuario = ctrlUsu.findUsuarios(login, senha);

            if (usuario == null) {
                // Se as credenciais forem inválidas, encerrar a conexão
                try (s1) {
                    // Se as credenciais forem inválidas, encerrar a conexão
                    System.out.println("Credenciais inválidas. Desconectando cliente.");
                }
                return;
            }

            // Loop de resposta
            while (true) {
                String comando = (String) in.readObject();

                if (comando.equals("L")) {
                    // Se o comando for 'L', retornar o conjunto de produtos
                    List<Produtos> produtos;
                    produtos = ctrl.listarProdutos();
                    out.writeObject(produtos);
                } else {
                    // Tratar outros comandos conforme necessário
                    // Você pode adicionar lógica para outros comandos aqui
                }
            }
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                s1.close();
            } catch (IOException e) {
            }
        }
    }
}
