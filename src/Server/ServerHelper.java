package Server;

import java.io.*;

/**
 * Created by Стас on 05.07.2016.
 */
final class ServerHelper {

    public static final String ENTER = "\n";
    public static final String CHAT_HISTORY = "MessageHistory.txt";
    public static final String SEPARATOR = ": ";

    private byte[] buf;

    public ServerHelper(byte[] buf) {
        this.buf = buf;
    }

    public String setHistory(String clientName) {
        String text = readISMessage();
        try (FileWriter writer = new FileWriter(CHAT_HISTORY, true)) {
            writer.write(clientName + SEPARATOR + text);
            writer.write(ENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public boolean isExists() {
        for (byte b : buf) {
            if(b != 0){
                return true;
            }
        }
        return false;
    }

    private String readISMessage() {
        StringBuilder sb = new StringBuilder(buf.length);
        for (byte b : buf) {
            sb.append((char) b);
        }
        return sb.toString().trim();
    }

}
