import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started successfully");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String inMessage;
                    String outMessage;
                    String[] name = {""};
                    while ((inMessage = in.readLine()) != null) {
                        outMessage = dialogue(inMessage, name);
                        if (outMessage == null) {
                            out.println("Bye");
                            break;
                        }
                        out.println(outMessage);
                    }
                } catch (IOException e) {
                    System.out.println("ERROR Server failed to process client connection: " + e.getMessage());
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("ERROR Server failure: " + e.getMessage());
            System.exit(1);
        }
    }

    private static String dialogue(String message, String[] name) {
        System.out.println("Server received [" + message + "]");
        String m = message.strip().toLowerCase();
        if (m.startsWith("hello")) {
            return "Hello. Write your name";
        } else if (m.equals("yes")) {
            return "Welcome to the kids area, " + name[0] + "! Let's play!";
        } else if (m.equals("no")) {
            return "Welcome to the adult zone, " + name[0] + "! Have a good rest, or a good working day!";
        } else if (m.startsWith("bye")) {
            return null;
        } else {
            // Everything else is considered to be the name of the client
            name[0] = message.strip();
            return "Are you child? (yes/no)";
        }
    }
}
