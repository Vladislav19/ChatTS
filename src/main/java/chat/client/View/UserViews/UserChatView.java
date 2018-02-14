package chat.client.View.UserViews;

import chat.Model.User;
import chat.client.ClientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class UserChatView {

    User user;

    Scanner in = new Scanner(System.in);
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    public UserChatView(User user, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.user = user;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void showChat(){

        System.out.println("User Chat+\n");
        System.out.println("Type your message");
        String message = in.nextLine();
        user.setMessage(message);

        try {
            objectOutputStream.flush();
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            String str = objectInputStream.readUTF();
            System.out.println(str);
            objectOutputStream.writeUTF("SendMessage");
            objectOutputStream.flush();
            String response = objectInputStream.readUTF();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
