package chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {

    private  static final int    serverPort = 6667;
    private  static final String localhost  = "127.0.0.1";

    public Socket getConnection(){
        Socket socket = null;
        try {
            System.out.println("Welcome to Client side\n" +
                    "Connecting to the server\n\t" +
                    "(IP address " + localhost +
                    ", port " + serverPort + ")");
            InetAddress ipAddress = InetAddress.getByName(localhost);
            socket = new Socket(ipAddress, serverPort);
            System.out.println("The connection is established.");

            System.out.println(
                    "\tLocalPort = " +
                            socket.getLocalPort() +
                            "\n\tInetAddress.HostAddress = " +
                            socket.getInetAddress().getHostAddress() +
                            "\n\tReceiveBufferSize (SO_RCVBUF) = " +
                            socket.getReceiveBufferSize());
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
