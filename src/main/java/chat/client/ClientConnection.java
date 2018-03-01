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
            InetAddress ipAddress = InetAddress.getByName(localhost);
            socket = new Socket(ipAddress, serverPort);
        }
        catch (Exception ex){
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
        }
    }
}
