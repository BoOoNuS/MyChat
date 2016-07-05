package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Стас on 04.07.2016.
 */
public class ViewChat extends Thread{

    @Override
    public void run() {
        OutputStream out = null;
        InputStream is = null;
        byte[] buf;

        while (true) {
            try (Socket socket = new Socket("localhost", 80)) {
                out = socket.getOutputStream();
                is = socket.getInputStream();
                out.write(0);
                buf = new byte[1024 * 64];
                is.read(buf);
                if(isExists(buf)) {
                    System.out.println(readISMessage(buf));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isExists(byte[] buf) {
        for (byte b : buf) {
            if(b != 0){
                return true;
            }
        }
        return false;
    }

    private static String readISMessage(byte[] buf) {
        StringBuilder sb = new StringBuilder(buf.length);
        for (byte b : buf) {
            sb.append((char) b);
        }
        return sb.toString();
    }

}
