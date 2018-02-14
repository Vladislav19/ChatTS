package chat.client.View;

import chat.Model.Agent;
import chat.Model.User;
import chat.client.Client;
import chat.client.ClientConnection;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class Registration {
    Scanner in = new Scanner(System.in);
    ClientConnection clientConnection  = null;
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;

    public void doRegistr() throws IOException {
        System.out.println("Type your role (USER or AGENT)");
        String role = in.nextLine();
        Switch(role);
    }

    public void Switch(String str){
        switch (str){
            case "USER":
                User user = new User();
                System.out.println("Type your login");
                user.setLogin(in.nextLine());
                System.out.println("Type your password");
                user.setPass(in.nextLine());
                sendUserData(user);
                break;

            case "AGENT":
                Agent agent = new Agent();
                System.out.println("Type your login");
                agent.setLogin(in.nextLine());
                System.out.println("Type your password");
                agent.setPass(in.nextLine());
                sendAgentData(agent);
                break;
            default:
                System.out.println("You misclick, retype please");
                Switch(in.nextLine());
        }
    }

    public  void sendUserData(User user){
        try {
            clientConnection  = new ClientConnection();
            socket = clientConnection.getConnection();
            objectOutputStream = clientConnection.getOutStream(socket);
            objectOutputStream.flush();
            objectInputStream = clientConnection.getInStream(socket);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            String str = objectInputStream.readUTF();
            System.out.println(str);
            objectOutputStream.writeUTF("RegUSER");
            objectOutputStream.flush();
            str = objectInputStream.readUTF();
            System.out.println(str);
            clientConnection.closeConnection(socket);
            Client.menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void sendAgentData(Agent agent){
        try {
            clientConnection  = new ClientConnection();
            socket = clientConnection.getConnection();
            objectOutputStream = clientConnection.getOutStream(socket);
            objectOutputStream.flush();
            objectInputStream = clientConnection.getInStream(socket);
            objectOutputStream.writeObject(agent);
            objectOutputStream.flush();
            String str = objectInputStream.readUTF();
            System.out.println(str);
            objectOutputStream.writeUTF("RegAGENT");
            objectOutputStream.flush();
            str = objectInputStream.readUTF();
            System.out.println(str);
            clientConnection.closeConnection(socket);
            Client.menu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

