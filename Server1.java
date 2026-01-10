
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
                System.out.println("reading agent bytes...");
                // AgentImpl agent = (AgentImpl) ois.readObject();
                // System.out.println("reading agent object...");

                AgentClassLoader loader = new AgentClassLoader();
                Class<?> agentClass = loader.loadClassFromBytes("AgentImpl", agentBytes);
                System.out.println("loading agent class...");

                // recuperation de l'agent envoyé dans la socket
                Agent agent = (AgentImpl) ois.readObject();
                System.out.println("reading agent object...");
                // AgentImpl agent = (AgentImpl) ois.readObject();
                // System.out.println("reading agent object...");

                agent.main();
                System.out.println("executing agent...");
                //System.out.println("Reception des bytes de l'agent:" + agentBytes);
                
                
    
                
                
            }

        } catch (Exception e) {
            System.out.println("Erreur dans Server1: " + e.getMessage());
        }

        



    }
    
}
