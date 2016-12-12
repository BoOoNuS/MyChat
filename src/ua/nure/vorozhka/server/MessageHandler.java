package ua.nure.vorozhka.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Stanislav on 11.12.2016.
 */
public class MessageHandler extends Thread {

    public static final String USER_HAS_DISCONNECTED_EXCEPTION = "User has disconnected";

    private Socket socket;

    public MessageHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while(true) {
                byte[] byteMessage = new byte[24*100];
                dis.read(byteMessage);
                String message = new String(byteMessage).trim();
                System.out.println(message);
                if(message.contains("close")){
                    socket.close();
                    ChatServer.deleteClosed();
                }
                for (Socket user : ChatServer.getSockets()) {
                    user.getOutputStream().write(message.getBytes());
                }
            }
        } catch (IOException e) {
            System.out.println(USER_HAS_DISCONNECTED_EXCEPTION);
        }
    }
}
