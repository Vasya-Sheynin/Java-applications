package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Thread for clients
 */
public class ThreadClient implements Runnable {

    private final BufferedReader cin;

    public ThreadClient(Socket socket) throws IOException {
        this.cin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = cin.readLine();
                if (message != null) {
                    System.out.println(message);
                } else {
                    System.out.println("You were disconnected from server. Enter anything to quit.");
                    Client.isDone = true;
                    break;
                }
            }
        } catch (SocketException e) {
            System.out.println("You left the chat-room");
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        } finally {
            try {
                cin.close();
            } catch (Exception exception) {
                exception.printStackTrace(System.out);
            }
        }
    }
}