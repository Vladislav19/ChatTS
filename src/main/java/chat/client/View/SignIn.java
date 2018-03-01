package chat.client.View;

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
            objectOutputStream.writeUTF("Autorisation");
            objectOutputStream.flush();
            Object object = objectInputStream.readObject();

            if(object==null){
                System.out.println("Login or password have mistake. Retype please");
                doSignIn();
            }
            else if(((User)object).getRole().equals("USER")){
                log.info("User: "+ ((User) object).getLogin() +" sign in system");
                MainUserView mainUserView = new MainUserView((User)object,objectOutputStream, objectInputStream);
                mainUserView.showMenuUser();
            }
            else if(((User)object).getRole().equals("AGENT")){
                log.info("Agent: "+ ((User) object).getLogin() +" sign in system");
                ChatAgent chatAgent = new ChatAgent(socket.getLocalPort());
                chatAgent.startChat();
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }
}
