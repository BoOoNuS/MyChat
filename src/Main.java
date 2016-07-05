import Client.Sender;
import Client.ViewChat;

/**
 * Created by Стас on 05.07.2016.
 */
public class Main {

    public static void main(String[] args) {
        new ViewChat().start();
        while(true){
            new Sender().sendMessage();
        }
    }

}
