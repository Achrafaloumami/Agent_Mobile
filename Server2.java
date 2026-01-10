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
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Agent agent = (Agent) ois.readObject();
                agent.main();
                ois.close();


                Socket socket2 = new Socket("localhost", 8091);

                ObjectOutputStream oos = new ObjectOutputStream(socket2.getOutputStream());
                oos.writeObject(agent);
                oos.flush();
                oos.close();
            }

        } catch (Exception e) {
            System.out.println("Erreur dans Server2: " + e.getMessage());
        }

        



    }
    
}
