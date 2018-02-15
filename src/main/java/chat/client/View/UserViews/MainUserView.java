package chat.client.View.UserViews;

import chat.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainUserView {
    Scanner in = new Scanner(System.in);
    User user;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    public MainUserView(User user, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.user = user;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void showMenuUser() throws IOException {
        System.out.println("User menu+\n");
        System.out.println("1) Write message to agent");
        System.out.println("2) Exit");
        Switch(in.nextLine());
    }

    public void Switch(String str){
        switch (str){
            case "1":UserChatView userChatView = new UserChatView(user,objectOutputStream, objectInputStream);
                try {
                    userChatView.showChat();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case "2":System.exit(0);
        }
    }
}
