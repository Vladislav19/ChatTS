package chat.client.View.AgentView;

import chat.client.ClientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class ChatAgent {

    Scanner in = new Scanner(System.in);
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;

    public ChatAgent(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void startChat(){
        System.out.println("Agent chat");
        try {
            String messageFromUser = objectInputStream.readUTF();
            System.out.println(messageFromUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
