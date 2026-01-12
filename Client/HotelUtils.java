import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HotelUtils {


    // Reads hotel names from a file and returns them as a list
    public static List<String> readHotelNames(String path) throws IOException {

        List<String> hotels = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            hotels.add(parts[0]);
        }

        reader.close();
        return hotels;
    }



    // read hotel telephone numbers from a file and returns them as a dictionary
    public static Map<String, String> readHotelPhones(String path) throws IOException {

        Map<String, String> hotelPhones = new java.util.HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            hotelPhones.put(parts[0], parts[1]);
        }

        reader.close();
        return hotelPhones;
    }


}