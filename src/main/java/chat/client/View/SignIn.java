package chat.client.View;

import chat.Model.Agent;
import chat.Model.User;
import chat.client.ClientConnection;
import chat.client.View.AgentView.ChatAgent;
import chat.client.View.UserViews.MainUserView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

/**
 * Created by Владислав on 14.02.2018.
 */
public class SignIn {

    Scanner in = new Scanner(System.in);
    ClientConnection clientConnection  = null;
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;
    private static final Logger log = Logger.getLogger(SignIn.class);

    public void doSignIn(){
        System.out.println("");
        System.out.println("Type your login");
        String login = in.nextLine();
        System.out.println("Type your password");
        String pass = in.nextLine();
        String md5Hex = DigestUtils.md5Hex(pass);


        try {
            clientConnection  = new ClientConnection();
            socket = clientConnection.getConnection();
            objectOutputStream = clientConnection.getOutStream(socket);
            objectOutputStream.flush();
            objectInputStream = clientConnection.getInStream(socket);
            objectOutputStream.writeObject(login+" "+md5Hex+" "+socket.getLocalPort()+" "+socket.getInetAddress());
            objectOutputStream.flush();
            String str = objectInputStream.readUTF();
            System.out.println(str);
            objectOutputStream.writeUTF("Autorisation");
            objectOutputStream.flush();
            Object object = objectInputStream.readObject();

            if(object instanceof User){
                log.info("User: "+ ((User) object).getLogin() +" sign in system");
                MainUserView mainUserView = new MainUserView((User)object,objectOutputStream, objectInputStream);
                mainUserView.showMenuUser();
            }
            else if(object instanceof Agent){
                log.info("Agent: "+ ((Agent) object).getLogin() +" sign in system");
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
