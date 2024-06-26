package client;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static boolean isDone = false;
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println(	"Invalid number of arguments\n" + "Use: [host] or nothing" );
            return;
        }

        String name = "";
        String reply = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name (Please enter your name to join the chat): ");
        reply = sc.nextLine();
        name = reply;

        try ( Socket socket = (args.length == 0 ?
                new Socket(InetAddress.getLocalHost(), 5060) :
                new Socket(args[0], 5060))) {
            PrintWriter cout = new PrintWriter(socket.getOutputStream(), true);

            ThreadClient threadClient = new ThreadClient(socket);
            new Thread(threadClient).start();

            cout.println(reply + ": has joined chat-room.");
            do {
                String message = (name + " : ");
                reply = sc.nextLine();
                if (reply.equals("logout")) {
                    cout.println("logout");
                    break;
                }
                cout.println(message + reply);
            } while (!reply.equals("logout") && !isDone);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}