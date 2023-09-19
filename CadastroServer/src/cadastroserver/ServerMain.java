import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServerMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NomeDaUnidadeDePersistencia");
        ProdutoJpaController ctrl = new ProdutoJpaController(emf);
        UsuarioJpaController ctrlUsu = new UsuarioJpaController(emf);

        try (ServerSocket serverSocket = new ServerSocket(4321)) {
            System.out.println("Servidor pronto para receber conexões na porta 4321...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                CadastroThread thread = new CadastroThread(clientSocket, ctrl, ctrlUsu);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            emf.close();
        }
    }
}
