package ua.nure.vorozhka.client;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Stanislav on 11.12.2016.
 */
public class ChatScanner extends Thread {

    public static final String SERVER_HAS_STOPPED_EXCEPTION = "Server has stopped";

    private InputStream is;

    public ChatScanner(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] byteMessage = new byte[24*100];
                is.read(byteMessage);
                System.out.println(new String(byteMessage).trim());
            }
        } catch (IOException e) {
            System.out.println(SERVER_HAS_STOPPED_EXCEPTION);
            System.exit(0);
        }
    }
}
