
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class AgentImpl implements Agent {

    private HashMap<String, Integer> Servers = new HashMap<>();
    private int nextServerIndex = 0;
    private byte[] agentCode;
    



    public AgentImpl(byte[] agentCode) {
        this.agentCode = agentCode;
        Servers.put("Server1", 8081);
        Servers.put("Server2", 8082);
    }




    @Override
    public void main() {
        // System.out.println("Agent is running");

        if (nextServerIndex == 0) {
            System.out.println("Agent Starting its journey...");
            this.nextServerIndex = 1;
            move("Server1");

        } else if (nextServerIndex == 1) {
            System.out.println("Agent arrived at Server1");
            // Perform some task at Server1
            this.nextServerIndex = 2;
            move("Server2");

        } else if (nextServerIndex == 2) {
            System.out.println("Agent arrived at Server2");
            // Perform some task at Server2
            nextServerIndex ++;
            back();

        } else {
            System.out.println("Agent returning to Client");
            // Return to Client
        }





    }


    public void move(String destination) {
        // Logic for moving the agent
        System.out.println("Moving to " + destination);
        try {
            Socket socket = new Socket("localhost", Servers.get(destination));
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(agentCode);
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
            oos.writeObject(agentCode);
            oos.writeObject(this);
            oos.flush();
        } catch (Exception e) {
            System.out.println("Erreur lors du retour de l'agent vers le client : " + e.getMessage());
        }
    }
    
}






