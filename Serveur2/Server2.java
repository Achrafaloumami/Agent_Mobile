import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

    public static void main(String[] args) {

        System.out.println("Server2 started on port 8082");

        try (ServerSocket serverSocket = new ServerSocket(8082)) {

            while (true) {

                System.out.println("Server2 waiting for agent...");
                Socket socket = serverSocket.accept();

                

                // ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


                AgentInputStream ois = new AgentInputStream(socket.getInputStream());



                byte[] jarBytes = (byte[]) ois.readObject();

                // Files.write(Path.of("agent.jar"), jarBytes);


                // 4. Charger dynamiquement les classes depuis le JAR
                JarClassLoader jarLoader = new JarClassLoader(jarBytes, Server2.class.getClassLoader());
                
                ois.setCustomLoader(jarLoader);
            

                Object agent = ois.readObject();
                Method m = agent.getClass().getMethod("main");

                m.invoke(agent);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class AgentInputStream extends ObjectInputStream {
        private ClassLoader customLoader;

        public AgentInputStream(InputStream in) throws IOException {
            super(in);
        }

        public void setCustomLoader(ClassLoader loader) {
            this.customLoader = loader;
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            if (customLoader != null) {
                try {
                    // On essaie de charger la classe via le JAR reçu
                    return customLoader.loadClass(desc.getName());
                } catch (ClassNotFoundException e) {
                    // Si non trouvé, on laisse le comportement normal
                    return super.resolveClass(desc);
                }
            }
            return super.resolveClass(desc);
        }
    }


}



























// import java.io.*;
// import java.net.ServerSocket;
// import java.net.Socket;

// public class Server2 {

//     public static void main(String[] args) {
//         System.out.println("Server2 started");
//         try {
//             ServerSocket server2socket = new ServerSocket(8082);

//             while (true) {
//                 System.out.println("Server2 en attente de l'agent");
//                 Socket socket = server2socket.accept();
//                 System.out.println("Connexion acceptee du client: ");
//                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                
//                 byte[] agentBytes = (byte[]) ois.readObject();

//                 AgentClassLoader loader = new AgentClassLoader();
//                 Class<?> agentClass = loader.loadClassFromBytes("AgentImpl", agentBytes);

//                 Agent agent = (AgentImpl) ois.readObject();
//                 System.out.println("reading agent object...");
//                 agent.main();

//                 //System.out.println("Reception des bytes de l'agent:" + agentBytes);
                
                
    
                
                
//             }

//         } catch (Exception e) {
//             System.out.println("Erreur dans Server2: " + e.getMessage());
//         }

        



//     }
    
// }
