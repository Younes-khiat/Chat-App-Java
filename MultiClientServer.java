import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiClientServer {
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        int port = 1675;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            // Infinite loop to accept multiple clients
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept new client
                System.out.println("New client connected: " + clientSocket);

                // Start a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, clients);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ClientHandler class to handle individual client communication
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private final List<ClientHandler> clients;
    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true)
        ) {
            String clientMessage;

            out.println("Welcome!" + clients + " Type your messages (type 'FIN' to exit).");

            // Handle communication with this client
            while ((clientMessage = in.readLine()) != null) {
                if (clientMessage.equalsIgnoreCase("FIN")) {
                    out.println("Goodbye!");
                    break;
                }
                System.out.println("Client [" + clients + "]: " + clientMessage);
                out.println("Server received: " + clientMessage);
            }

        } catch (IOException e) {
            System.out.println("Error with client [" + clientSocket + "]: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
