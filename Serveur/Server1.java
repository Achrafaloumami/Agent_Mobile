import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {

    public static void main(String[] args) {

        System.out.println("Server1 started on port 8081");

        try (ServerSocket serverSocket = new ServerSocket(8081)) {

            while (true) {

                System.out.println("Server1 waiting for agent...");
                Socket socket = serverSocket.accept();

                

                // ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


                AgentInputStream ois = new AgentInputStream(socket.getInputStream());



                byte[] jarBytes = (byte[]) ois.readObject();

                // Files.write(Path.of("agent.jar"), jarBytes);


                // 4. Charger dynamiquement les classes depuis le JAR
                JarClassLoader jarLoader = new JarClassLoader(jarBytes, Server1.class.getClassLoader());
                
                // 5. Charger la classe de l'agent avec le JarClassLoader
                // Class<?> agentClass = jarLoader.loadAgentClass(agent.getClass().getName());





                // Object agent = ois.readObject();

                // Method main = agent.getClass().getMethod("main");
                // main.invoke(agent);

        

                // ObjectInputStream agentStream = new ObjectInputStream(socket.getInputStream()) {
                //     @Override
                //     protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                //         return jarLoader.loadClass(desc.getName());
                //     }
                // };

                // Injecter le JarClassLoader dans l'ObjectInputStream
                
                
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
