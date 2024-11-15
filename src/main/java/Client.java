import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("netology.homework", Server.SERVER_PORT);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            writer.println("Hello, Server");

            String inMessage;
            String outMessage;
            while ((inMessage = reader.readLine()) != null) {
                System.out.println("Client received [" + inMessage + "]");
                inMessage = inMessage.strip().toLowerCase();
                if (inMessage.contains("write your name")) {
                    outMessage = dialogue("name");
                } else if (inMessage.contains("are you child")) {
                    outMessage = dialogue("age");
                } else {
                    outMessage = "Bye";
                }
                writer.println(outMessage);
            }
        } catch(IOException | IllegalArgumentException e) {
            System.out.println("ERROR Client failure: " + e.getMessage());
            System.exit(1);
        }
    }

    private static String dialogue(String key) {
        Map<String, List<String>> answers = new HashMap<>();
        answers.put("name", List.of("Alice", "Bob", "Charlie", "David", "Eva"));
        answers.put("age", List.of("yes", "no"));

        List<String> choice = answers.get(key);
        Random rand = new Random();
        return choice.get(rand.nextInt(choice.size()));
    }
}
