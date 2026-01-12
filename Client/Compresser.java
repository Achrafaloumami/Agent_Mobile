import java.io.*;
import java.util.zip.GZIPOutputStream;

public class Compresser {

    public static byte[] compress(String path) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        FileInputStream fileInputStream = new FileInputStream(path);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read(buffer)) != -1) {
            gzipOutputStream.write(buffer, 0, len);
        }
        fileInputStream.close();
        gzipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
        
        
        // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        // GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        // FileInputStream fileInputStream = new FileInputStream(file);
        // byte[] buffer = new byte[1024];
        // int len;
        // while ((len = fileInputStream.read(buffer)) != -1) {
        //     gzipOutputStream.write(buffer, 0, len);
        // }
        // fileInputStream.close();
        // gzipOutputStream.close();

        // return byteArrayOutputStream.toByteArray();
    }


}
