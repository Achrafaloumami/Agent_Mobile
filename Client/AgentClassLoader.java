public class AgentClassLoader extends  ClassLoader {
    


    public Class<?> loadClassFromBytes(String className, byte[] classBytes) throws ClassNotFoundException {
        return defineClass(className, classBytes, 0, classBytes.length);
    }



}
