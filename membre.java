import java.io.*;
import java.net.*;

public class membre {
    public static void main(String[] args) throws Exception{
        // 1-CONSTRUCTIONN DU PORT DE COMMUNICATION
        // 1-1 RECUPERATION DE L’ADRESSE IP
        InetAddress addr= InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);
        // 1-2: DEMANDE DE CONNEXIONN AU SERVEUR
        Socket cs = new Socket(addr, 1675);
        // LA CONNECTION EST ETABLIE
        // LA CONNECTION EST ETABLIE
        // RECUPERATION DES FLUX E/S DE LA CONNEXION
        // 2 FLUX
        BufferedReader in = new BufferedReader(
        new InputStreamReader(cs.getInputStream()));
        PrintWriter out = new PrintWriter(
        new BufferedWriter( new OutputStreamWriter(
        cs.getOutputStream())),true);
        // PREPARATION DU FLUX STANDARD CLAVIER
        BufferedReader inConsole = new BufferedReader(
        new InputStreamReader( System.in));
        // COMMUNICATION EFFECTIVE
        // COMMUNICATION EFFECTIVE
        String lineConsole, lineServer;
        while ((lineConsole = inConsole.readLine()) != null) {
            out.println(lineConsole); // Send to server
            if (lineConsole.equalsIgnoreCase("FIN")) {
                break;
            }
            lineServer = in.readLine(); // Read from server
            System.out.println("Server: " + lineServer);
        }
        cs.close();
        System.out.println("Client Down!");
    }

}
