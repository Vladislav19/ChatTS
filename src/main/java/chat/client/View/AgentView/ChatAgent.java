package chat.client.View.AgentView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Владислав on 14.02.2018.
 */
public class ChatAgent {

    int port;
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
                System.out.println("Exception : " + e);
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
