
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

    public static void main(String[] args) {
        System.out.println("Server2 started");
        try {
            ServerSocket server2socket = new ServerSocket(8082);

            while (true) {
                System.out.println("Server2 en attente de l'agent");
                Socket socket = server2socket.accept();
                System.out.println("Connexion acceptee du client: ");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                
                byte[] agentBytes = (byte[]) ois.readObject();

                AgentClassLoader loader = new AgentClassLoader();
                Class<?> agentClass = loader.loadClassFromBytes("AgentImpl", agentBytes);

                Agent agent = (AgentImpl) ois.readObject();
                System.out.println("reading agent object...");
                agent.main();

                //System.out.println("Reception des bytes de l'agent:" + agentBytes);
                
                
    
                
                
            }

        } catch (Exception e) {
            System.out.println("Erreur dans Server2: " + e.getMessage());
        }

        



    }
    
}
