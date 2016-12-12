package ua.nure.vorozhka.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 11.12.2016.
 */
public class ChatServer {

    public static final int PORT = 8888;
    public static final String SERVER_HAS_STARTED_MESSAGE = "Server has started";
    public static final String CLIENT_CONNECTED_MESSAGE = "Client connected";
    private static List<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println(SERVER_HAS_STARTED_MESSAGE);
            while (true){
                Socket socket = serverSocket.accept();
                if(!sockets.contains(socket)) {
                    sockets.add(socket);
                }
                new MessageHandler(socket).start();
                System.out.println(CLIENT_CONNECTED_MESSAGE);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Socket> getSockets() {
        return sockets;
    }

    public static void deleteClosed(){
        for (Socket socket : sockets) {
            if(socket.isClosed()){
                sockets.remove(socket);
            }
        }
    }
}
