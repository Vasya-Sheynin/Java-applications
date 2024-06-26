package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        ArrayList<Socket> clients = new ArrayList<>();
        HashMap<Socket, String> clientNameList = new HashMap<Socket, String>();

        try (ServerSocket serversocket = new ServerSocket(5060)) {
            System.out.println("Server is started...");
            ServerStopThread tester = new ServerStopThread();
            tester.start();
            while (true) {
                Socket socket = accept(serversocket);
                if (socket != null) {
                    clients.add(socket);
                    ThreadServer ThreadServer = new ThreadServer(socket, clients, clientNameList);
                    ThreadServer.start();
                }
                if (Server.getStopFlag()) {
                    for (Socket sock : clients) {
                        sock.close();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static Socket accept( ServerSocket serv ) {
        assert(serv != null);
        try {
            serv.setSoTimeout( 1000 );
            return serv.accept();
        } catch (SocketException e) {
        } catch (IOException e) {
        }
        return null;
    }

    private static final Object syncFlags = new Object();
    private static boolean stopFlag = false;
    public static boolean getStopFlag() {
        synchronized (Server.syncFlags ) {
            return stopFlag;
        }
    }
    public static void setStopFlag( boolean value ) {
        synchronized (Server.syncFlags ) {
            stopFlag = value;
        }
    }
}

class ServerStopThread extends Thread {

    Scanner fin;

    public ServerStopThread() {
        fin = new Scanner(System.in);
        Server.setStopFlag(false);
        this.setDaemon(true);
        System.out.println( "Enter \"stop\" to stop server\n" );
    }

    public void run() {

        while (true) {
            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                break;
            }
            if (!fin.hasNextLine())
                continue;
            String str = fin.nextLine();
            if (Objects.equals(str, "stop")) {
                onCmdStop();
                break;
            }
        }
    }

    public void onCmdStop() {
        System.out.println("Stopping server...");
        fin.close();
        Server.setStopFlag(true);
    }
}

