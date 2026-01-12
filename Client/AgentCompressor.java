

public class AgentCompressor extends AgentImpl {

    private int nextServerIndex = 0;
    private byte[] resultat;
    private String filename;
    

    public AgentCompressor(byte[] jarBytes, String filename) {
        super(jarBytes);
        this.filename = filename;
        Servers.put("Server", 8081);
    }


    
    public byte[] getResultat() {
        return this.resultat;
    }





    @Override
    public void main() {

        if (nextServerIndex == 0) {
            System.out.println("Agent Starting its journey...");
            this.nextServerIndex = 1;
            move("Server");

        } else if (nextServerIndex == 1) {
            System.out.println("Agent arrived at Server");
            // Perform some task at Server

            try {
                System.out.println("Compressing file: " + this.filename);
                this.resultat = Compresser.compress("data/" + this.filename);
                System.out.println("File compressed");
            } catch (Exception e) {
                System.out.println("Erreur lors de la compression du fichier: " + e.getMessage());
                e.printStackTrace();
            }

            
            // Prepare to return to Client

            nextServerIndex ++;
            back();

        } else {
            System.out.println("Agent returning to Client");
            // Return to Client
            System.out.println("Resultat collected: " + this.resultat);
        }





    }

}






