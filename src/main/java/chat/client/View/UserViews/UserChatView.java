package chat.client.View.UserViews;

import chat.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;import org.apache.log4j.Logger;

/**
 * Created by Владислав on 14.02.2018.
 */
public class UserChatView {

    User user;
    int port;String ip;
    String address;
    Scanner in = new Scanner(System.in);
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    private static final Logger log = Logger.getLogger(UserChatView.class);
    Socket socket = null;
    ObjectOutputStream ous;
    ObjectInputStream ois;

    public UserChatView(User user, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.user = user;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void showChat() throws IOException {

        System.out.println("User Chat+\n");
        try {
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
            objectOutputStream.writeUTF("GetFreeAgentPort");
            objectOutputStream.flush();
            address = objectInputStream.readUTF();
            String[] mas = address.split(" ");
            port = Integer.parseInt(mas[1]);
            ip = mas[0];

            if(port==-1){
                System.out.println("Wait a free agent");
                Thread.sleep(1000);
                showChat();
            }
            else{
                socket = createConnectionThisAgent();
                ois = getInStream(socket);
                ous = getOutStream(socket);
                ous.flush();
                log.info("User "+ user.getLogin()+" start chat");
            }

        }catch (IOException ex){
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int i=0;
        System.out.println("Type your message");
        while(true) {
            if(i!=0){
                System.out.println(ois.readUTF());
            }
            String message = in.nextLine();
            if (message.equals("Thanks")) {
                closeConnection(socket);
                log.info("User "+ user.getLogin()+" end chat");
                MainUserView mainUserView = new MainUserView(user, objectOutputStream, objectInputStream);
                mainUserView.showMenuUser();

            }
            if (message.equals("Exit")) {
                log.info("User "+ user.getLogin()+" end chat");
                closeConnection(socket);
                System.exit(0);
            }
            ous.writeUTF(message);
            ous.flush();
            System.out.println(ois.readUTF());
            i++;
        }
    }

    public Socket createConnectionThisAgent(){
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            socket = new Socket(ipAddress, port);
            System.out.println("The connection this Agent is established.");

        }
        catch (ConnectException e){
            try {
                objectOutputStream.writeObject(port);
                objectOutputStream.flush();
                objectOutputStream.writeUTF("AgentIsNotActive");
                objectOutputStream.flush();
                showChat();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return  socket;
    }

    public ObjectOutputStream getOutStream(Socket socket) throws IOException {
        return new  ObjectOutputStream(socket.getOutputStream());
    }

    public ObjectInputStream getInStream(Socket socket) throws IOException {
        return new  ObjectInputStream(socket.getInputStream());
    }

    public void closeConnection(Socket socket)throws IOException{
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
