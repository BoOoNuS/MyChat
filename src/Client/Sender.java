package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Стас on 04.07.2016.
 */
public class Sender {

    public static final String PUT_MESSAGE_TO_SERVER_MESSAGE = "Put message to server...";

    public void sendMessage(){
        try (Socket socket = new Socket("localhost", 80)){
            System.out.println(PUT_MESSAGE_TO_SERVER_MESSAGE);
            socket.getOutputStream().write(new Scanner(System.in).nextLine().getBytes());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
