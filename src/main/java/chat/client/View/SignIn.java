package chat.client.View;

import chat.Model.Agent;
import chat.Model.User;
import chat.client.Client;
import chat.client.ClientConnection;
import chat.client.View.AgentView.ChatAgent;
import chat.client.View.UserViews.MainUserView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class SignIn {

    Scanner in = new Scanner(System.in);
    ClientConnection clientConnection  = null;
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;

    public void doSignIn(){
        System.out.println("");
        System.out.println("Type your login");
        String login = in.nextLine();
        System.out.println("Type your password");
        String password = in.nextLine();

        try {
            clientConnection  = new ClientConnection();
            socket = clientConnection.getConnection();
            objectOutputStream = clientConnection.getOutStream(socket);
            objectOutputStream.flush();
            objectInputStream = clientConnection.getInStream(socket);
            objectOutputStream.writeObject(login+" "+password+" "+socket.getLocalPort());
            objectOutputStream.flush();
            String str = objectInputStream.readUTF();
            System.out.println(str);
            objectOutputStream.writeUTF("Autorisation");
            objectOutputStream.flush();
            Object object = objectInputStream.readObject();

            if(object instanceof User){
                System.out.println(((User)object).getId()+" "+((User)object).getLogin());
                MainUserView mainUserView = new MainUserView((User)object,objectOutputStream, objectInputStream);
                mainUserView.showMenuUser();
            }
            else if(object instanceof Agent){
                System.out.println(((Agent)object).getId()+" "+((Agent)object).getLogin());
                ChatAgent chatAgent = new ChatAgent(socket.getLocalPort());
                chatAgent.startChat();
                clientConnection.closeConnection(socket);
            }
            else {
                System.out.println("Not found, retype or data");
                doSignIn();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
