import java.io.*;
import java.util.*;
import java.util.jar.*;

public class JarClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes = new HashMap<>();

    public JarClassLoader(byte[] jarBytes, ClassLoader parent)
            throws IOException {
        super(parent);
        loadJar(jarBytes);
    }

    private void loadJar(byte[] jarBytes) throws IOException {

        try (JarInputStream jis = new JarInputStream(new ByteArrayInputStream(jarBytes))) {

            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {

                if (entry.getName().endsWith(".class")) {

                    String className = entry.getName().replace('/', '.').replace(".class", "");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    jis.transferTo(baos);

                    classes.put(className, baos.toByteArray());
                    System.out.println("Chargée: " + className);
                }
            }
        }
    }

    @Override
    protected Class<?> findClass(String name)
            throws ClassNotFoundException {

        byte[] classData = classes.get(name);

        if (classData == null) {
            throw new ClassNotFoundException(name);
        }

        return defineClass(name, classData, 0, classData.length);
    }
}
