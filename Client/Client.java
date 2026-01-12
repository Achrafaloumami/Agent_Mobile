import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Client {

    public Client() {
    }
    

    public static void main(String[] args) {
        System.out.println("Client started");

        String filename = "Loup.txt";

        try {
            System.out.println("lancement du agent");

            
            // -------------- Jar for Compression Agent -----------------------------------------------

            // byte[] jarBytesCompressor = Files.readAllBytes(Paths.get("agent_compression/agentCompressor.jar"));
            // AgentCompressor agent = new AgentCompressor(jarBytesCompressor, filename);


            // -------------- Jar for Hotel Agent -----------------------------------------------

            byte[] jarBytesHotels = Files.readAllBytes(Paths.get("agent_hotel/agentHotel.jar"));
            AgentHotels agent = new AgentHotels(jarBytesHotels);


            
            agent.main();

            


        } catch (Exception e) {
            System.out.println("Erreur lors du lancement d'agent: " + e.getMessage());
        }
        

        // Reception de l'agent retourner par le serveurs

        try {
            ServerSocket serverSocket = new ServerSocket(8091);
            System.out.println("en attente du l'agent");

            // while (true) {

                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


                // ------------------- Compression ---------------------------------------

                // AgentCompressor agent = (AgentCompressor) ois.readObject();

                // byte[] resultat = agent.getResultat();
                // Decompresser.decompressToFile(resultat, "data/decompressed_" + filename);



                // ------------------- Hotel ---------------------------------------

                AgentHotels agent = (AgentHotels) ois.readObject();
                System.out.println("reading agent object...");

                HashMap<String, String> resultat = agent.getResultat();
                System.out.println("affichage du resultat: " + resultat);

                for (String hotelName : resultat.keySet()) {
                    String phone = resultat.get(hotelName);
                    System.out.println("Hotel: " + hotelName + ", Phone: " + phone);
                }
                


            // }
        } catch (Exception e) {
            System.out.println("Erreur dans la reception d'agent: " + e.getMessage());
        }

    }
}
