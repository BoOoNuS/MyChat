package ua.nure.vorozhka.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav on 11.12.2016.
 */
public class ChatServer {

    public static final int PORT = 8888;
    public static final String SERVER_HAS_STARTED_MESSAGE = "Server has started";
    public static final String CLIENT_CONNECTED_MESSAGE = "Client connected";
    private static Map<String, Socket> users = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println(SERVER_HAS_STARTED_MESSAGE);
            while (true){
                Socket socket = serverSocket.accept();
                new MessageHandler(socket).start();
                System.out.println(CLIENT_CONNECTED_MESSAGE);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteClosed(){
        for (Map.Entry<String, Socket> stringSocketEntry : users.entrySet()) {
            if(stringSocketEntry.getValue().isClosed()){
                users.remove(stringSocketEntry.getKey(), stringSocketEntry.getValue());
            }
        }
    }

    public static Map<String, Socket> getUsers() {
        return users;
    }
}
