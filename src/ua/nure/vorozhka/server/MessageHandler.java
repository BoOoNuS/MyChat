package ua.nure.vorozhka.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 11.12.2016.
 */
public class MessageHandler extends Thread {

    public static final String USER_HAS_DISCONNECTED_EXCEPTION = "User has disconnected";

    private static final Pattern PATTERN_FOR_GETTING_NICK =
            Pattern.compile("(?<=^\\[)(\\w+)(?=\\])");

    private static final Pattern PATTERN_FOR_WRITE_TO =
            Pattern.compile("(?<=/w\\s\\[)(\\w+)(?=\\]\\s*)");

    private static final Pattern PATTER_FOR_GETTING_MESSAGE =
            Pattern.compile("(^\\[\\w+\\]\\s+)(/w\\s+\\[\\w+\\]\\s+)(.+)");

    public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    public static final String INCORRECT_SYNTAX_MESSAGE = "Incorrect syntax";

    private Socket socket;

    public MessageHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Matcher matcher;
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                byte[] byteMessage = new byte[24 * 100];
                dis.read(byteMessage);
                String message = new String(byteMessage).trim();
                matcher = PATTERN_FOR_GETTING_NICK.matcher(message);
                if (matcher.find()) {
                    String nick = matcher.group(1);
                    if (!ChatServer.getUsers().containsKey(nick)) {
                        ChatServer.getUsers().put(nick, socket);
                    }
                }

                System.out.println(message);
                if (message.contains("close")) {
                    socket.close();
                    ChatServer.deleteClosed();
                }

                matcher = PATTERN_FOR_WRITE_TO.matcher(message);
                if (matcher.find()) {
                    String nickTo = matcher.group(1);
                    if (ChatServer.getUsers().containsKey(nickTo)) {
                        for (Map.Entry<String, Socket> user : ChatServer.getUsers().entrySet()) {
                            if (user.getKey().equals(nickTo)) {
                                matcher = PATTER_FOR_GETTING_MESSAGE.matcher(message);
                                if (matcher.find()) {
                                    user.getValue().getOutputStream().
                                            write((matcher.group(1) + matcher.group(3)).getBytes());
                                } else {
                                    System.out.println(INCORRECT_SYNTAX_MESSAGE);
                                }
                            }
                        }
                    } else {
                        System.out.println(USER_NOT_FOUND_EXCEPTION);
                    }
                } else {
                    for (Socket user : ChatServer.getUsers().values()) {
                        user.getOutputStream().write(message.getBytes());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(USER_HAS_DISCONNECTED_EXCEPTION);
        }
    }
}
