import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public abstract class AgentImpl implements Agent {

    protected HashMap<String, Integer> Servers = new HashMap<>();
    private byte[] jarBytes;
    

    public AgentImpl(byte[] jarBytes) {
        this.jarBytes = jarBytes;
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
