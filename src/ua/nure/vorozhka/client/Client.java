package ua.nure.vorozhka.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Stanislav on 11.12.2016.
 */
public class Client {

    private static final Scanner SCANNER = new Scanner(System.in);
    public static final String ENTER_YOUR_MESSAGE_MESSAGE = "Enter your message";

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        String name = SCANNER.next();
        Socket socket = null;
        DataOutputStream dos = null;
        InputStream is = null;
        String message;
        try {
            socket = new Socket("localhost", 8888);
            dos = new DataOutputStream(socket.getOutputStream());
            is = socket.getInputStream();
            System.out.println(ENTER_YOUR_MESSAGE_MESSAGE);
            new ChatScanner(is).start();
            do {
                message = "[" + name + "] " + SCANNER.nextLine();
                dos.writeUTF(message);
            } while (!("[" + name + "] close").equals(message));
            close(socket, is, dos);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void close(Socket socket, InputStream is, OutputStream os) {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
