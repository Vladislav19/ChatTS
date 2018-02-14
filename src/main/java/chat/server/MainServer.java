package chat.server;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainServer {
    public static void main(String[] ar)
    {
        ServerConnetion serverConnetion = new ServerConnetion();
        serverConnetion.startServer();
    }
}
