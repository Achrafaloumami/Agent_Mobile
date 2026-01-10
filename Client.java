import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {

    public Client() {
    }
    

    public static void main(String[] args) {
        System.out.println("Client started");



        try {
            System.out.println("lancement du agent");
        
            byte[] agentCode = Files.readAllBytes(Paths.get("AgentImpl.class"));

            AgentImpl agent = new AgentImpl(agentCode);
            agent.main();

            // Socket socket = new Socket("localhost", 8081);
            // ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // oos.writeObject(agentCode);
            // oos.flush();

            // ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            // Agent receivedAgent = (Agent) ois.readObject();
            // receivedAgent.main();
            // ois.close();


        } catch (Exception e) {
            System.out.println("Erreur lors du lancement d'agent: " + e.getMessage());
        }
        



        try {
            ServerSocket serverSocket = new ServerSocket(8091);
            System.out.println("en attente du l'agent");

            // while (true) {

                

                
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                byte[] agentBytes = (byte[]) ois.readObject();
                AgentClassLoader loader = new AgentClassLoader();
                Class<?> agentClass = loader.loadClassFromBytes("AgentImpl", agentBytes);
                Agent agent = (AgentImpl) ois.readObject();
                System.out.println("reading agent object...");
                agent.main();

                // Agent agent = (Agent) ois.readObject();
                // agent.main();
                // ois.close();

            // }
        } catch (Exception e) {
            System.out.println("Erreur dans la reception d'agent: " + e.getMessage());
        }

        

        
    }
}
