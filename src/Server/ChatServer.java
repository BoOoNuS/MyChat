package Server;

import java.io.*;
import java.net.*;

/**
 * Created by Стас on 04.07.2016.
 */
public class ChatServer extends Thread {

    public static final String SERVER_STARTED_MESSAGE = "Server started!";
    private static volatile String message;
    private static volatile boolean changed;

    private Socket socket;

    public ChatServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(80);
            System.out.println(SERVER_STARTED_MESSAGE);
            while (true) {
                new ChatServer(server.accept()).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] buf = new byte[1024 * 64];
        OutputStream os = null;
        InputStream is = null;

        try {
            os = socket.getOutputStream();
            is = socket.getInputStream();
            is.read(buf);
            ServerHelper helper = new ServerHelper(buf);
            if (helper.isExists()) {
                message = helper.setHistory(socket.getInetAddress().getHostName());
                changed = true;
            }
            else {
                if(changed){
                    os.write(message.getBytes());
                    changed = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
