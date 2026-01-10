
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {

    public static void main(String[] args) {
        System.out.println("Server1 started");


        try {
            ServerSocket server1socket = new ServerSocket(8081);

            while (true) {
                System.out.println("Server1 en attente de l'agent");
                Socket socket = server1socket.accept();
                System.out.println("Connexion acceptee du client: ");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                
                byte[] agentBytes = (byte[]) ois.readObject();

                AgentClassLoader loader = new AgentClassLoader();
                Class<?> agentClass = loader.loadClassFromBytes("AgentImpl", agentBytes);
                Agent agent = (Agent) agentClass.getDeclaredConstructor().newInstance();
                agent.main();

                System.out.println("Reception des bytes de l'agent:" + agentBytes);
                
                
                // Agent agent = (Agent) ois.readObject();
                // agent.main();

                Socket socket2 = new Socket("localhost", 8082);
                ObjectOutputStream oos = new ObjectOutputStream(socket2.getOutputStream());
                oos.writeObject(agent);
                oos.flush();
                
                
            }

        } catch (Exception e) {
            System.out.println("Erreur dans Server1: " + e.getMessage());
        }

        



    }
    
}
