
import java.io.*;
import java.util.zip.GZIPInputStream;

public class Decompresser {

    public static void decompressToFile(byte[] data, String FileName) throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(data));
        FileOutputStream fileOutputStream = new FileOutputStream(FileName);

        byte[] buffer = new byte[1024];
        int len;

        while ((len = gzipInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, len);
        }

        gzipInputStream.close();
        fileOutputStream.close();
        
    }
    
}
