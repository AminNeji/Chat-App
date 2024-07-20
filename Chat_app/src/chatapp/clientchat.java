package chatapp;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class clientchat {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Consumer<String> onMessageReceived;

    clientchat(String adr, int port, Consumer<String> onMessageReceived) throws IOException {
        try {
            socket = new Socket(adr, port);
            System.out.println("Connected to the server");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.onMessageReceived = onMessageReceived;
            startClient();
        } catch (UnknownHostException e) {
            System.out.println("host unknown");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void startClient() {
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    onMessageReceived.accept(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
