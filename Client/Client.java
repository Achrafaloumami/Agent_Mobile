import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {

    public Client() {
    }
    

    public static void main(String[] args) {
        System.out.println("Client started");

        String filename = "Loup.txt";

        try {
            System.out.println("lancement du agent");
        
            

            byte[] jarBytes = Files.readAllBytes(Paths.get("agents/agent.jar"));


            


            AgentCompressor agent = new AgentCompressor(jarBytes, filename);


            
            agent.main();

            


        } catch (Exception e) {
            System.out.println("Erreur lors du lancement d'agent: " + e.getMessage());
        }
        



        try {
            ServerSocket serverSocket = new ServerSocket(8091);
            System.out.println("en attente du l'agent");

            // while (true) {

                

                
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                


                Agent agent = (AgentCompressor) ois.readObject();
                System.out.println("reading agent object...");

                byte[] resultat = agent.getResultat();

                Decompresser.decompressToFile(resultat, "data/decompressed_" + filename);

                

            // }
        } catch (Exception e) {
            System.out.println("Erreur dans la reception d'agent: " + e.getMessage());
        }

        

        
    }
}
