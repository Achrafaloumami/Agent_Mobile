import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        
        if (args[0] == null) {
            System.out.println("Usage: java Server1 <port>");

            return;

        } else {
            System.out.println("Starting Server1 on port " + args[0]);

            int port = Integer.parseInt(args[0]);

            try {

                ServerSocket serverSocket = new ServerSocket(port);

                while (true) {
                    System.out.println("Server1 waiting for agent...");
                    Socket socket = serverSocket.accept();

                    AgentInputStream ois = new AgentInputStream(socket.getInputStream());

                    byte[] jarBytes = (byte[]) ois.readObject();

                    // 4. Charger dynamiquement les classes depuis le JAR
                    JarClassLoader jarLoader = new JarClassLoader(jarBytes, Server.class.getClassLoader());                    
                    
                    ois.setCustomLoader(jarLoader);
                
                    Object agent = ois.readObject();

                    Method m = agent.getClass().getMethod("main");

                    m.invoke(agent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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
