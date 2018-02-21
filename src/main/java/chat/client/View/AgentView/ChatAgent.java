package chat.client.View.AgentView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Владислав on 14.02.2018.
 */
public class ChatAgent {

    int port;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    public ChatAgent(int port) {
        this.port = port;
    }

    public void startChat() throws IOException {
        System.out.println("Agent chat");
        creatAgentServer();
    }

    public void creatAgentServer(){
        ServerSocket srvSocket = null;
        try {
            try {
                InetAddress ia = InetAddress.getByName("localhost");
                srvSocket = new ServerSocket(port, 0, ia);
                System.out.println("Agent Server started\n\n");
                while(true) {
                    Socket socket = srvSocket.accept();
                    System.err.println("Client accepted");
                    new Server().setSocket(socket);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (srvSocket != null)
                    srvSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
