
import java.util.HashMap;
import java.util.List;

public class AgentHotels extends AgentImpl {

    private int nextServerIndex = 0;
    private List<String> hotelNames;
    private HashMap<String, String> hotelPhones;
    private HashMap<String, String> resultat = new HashMap<>();

    

    public AgentHotels(byte[] jarBytes) {

        super(jarBytes);
        Servers.put("Server1", 8081);
        Servers.put("Server2", 8082);

    }

    
    public HashMap<String, String> getResultat() {

        for (String nameHotel : hotelNames) {

            String phone = hotelPhones.get(nameHotel);
            if (phone != null) {
                resultat.put(nameHotel, phone);

            }
        }

        return this.resultat;
    }

    @Override
    public void main() {
        if (nextServerIndex == 0) {

            System.out.println("Agent Starting its journey...");
            this.nextServerIndex = 1;
            move("Server1");

        } else if (nextServerIndex == 1) {

            System.out.println("Agent arrived at Server");
            // Perform some task at Server

            try {
                System.out.println("Reading hotel names and phones...");
                hotelNames = HotelUtils.readHotelNames("serveur_1/hotels.txt");
            } catch (Exception e) {
                System.out.println("Error reading hotel data: " + e.getMessage());
                e.printStackTrace();
            }

            // Prepare to return to Client

            nextServerIndex++;
            move("Server2");

        } else if (nextServerIndex == 2) {
            System.out.println("Agent arrived at Server2");
            // Perform some task at Server2

            try {
                System.out.println("Reading hotel phone numbers...");
                hotelPhones = new HashMap<>(HotelUtils.readHotelPhones("serveur_2/hotel_phones.txt"));
            } catch (Exception e) {
                System.out.println("Error reading hotel phones: " + e.getMessage());
                e.printStackTrace();
            }

            // Prepare to return to Client
            nextServerIndex++;
            back();

        } else {
            System.out.println("Agent returning to Client");
            // Return to Client
        }

    }


}
