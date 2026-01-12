
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class AgentCompressor implements Agent {

    private HashMap<String, Integer> Servers = new HashMap<>();
    private int nextServerIndex = 0;
    private byte[] jarBytes;
    private byte[] resultat;
    private String filename;
    

    public AgentCompressor(byte[] jarBytes, String filename) {
        this.jarBytes = jarBytes;
        this.filename = filename;
        Servers.put("Server", 8081);
        
    }


    @Override
    public byte[] getResultat() {
        return this.resultat;
    }





    @Override
    public void main() {
        // System.out.println("Agent is running");

        if (nextServerIndex == 0) {
            System.out.println("Agent Starting its journey...");
            this.nextServerIndex = 1;
            move("Server");

        } else if (nextServerIndex == 1) {
            System.out.println("Agent arrived at Server");
            // Perform some task at Server


            

            try {
                System.out.println("Compressing file: " + this.filename);
                this.resultat = Compresser.compress("data/" + this.filename);
                System.out.println("File compressed");
            } catch (Exception e) {
                System.out.println("Erreur lors de la compression du fichier: " + e.getMessage());
                e.printStackTrace();
            }

            

            // Prepare to return to Client

            nextServerIndex ++;
            back();

        } else {
            System.out.println("Agent returning to Client");
            // Return to Client
            System.out.println("Resultat collected: " + this.resultat);
        }





    }


    public void move(String destination) {
        // Logic for moving the agent
        System.out.println("Moving to " + destination);
        try {
            Socket socket = new Socket("localhost", Servers.get(destination));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(jarBytes);
            oos.writeObject(this);
            oos.flush();

        } catch (Exception e) {
            System.out.println("Erreur lors du deplacement de l'agent vers " + destination + " : " + e.getMessage());
        }
        
    }


    public void back() {
        // Logic for returning to the client
        System.out.println("Returning to Client");
        try {
            Socket socket = new Socket("localhost", 8091);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // oos.writeObject(jarBytes);
            oos.writeObject(this);
            oos.flush();
        } catch (Exception e) {
            System.out.println("Erreur lors du retour de l'agent vers le client : " + e.getMessage());
        }
    }

    



    
    
}






