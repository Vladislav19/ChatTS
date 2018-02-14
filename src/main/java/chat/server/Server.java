package chat.server;


import chat.server.Controllers.MainController;

import java.io.*;
import java.net.*;

public class Server extends Thread
{
    private static final int port  = 6667; // открываемый порт сервера
    private String TEMPL_CONN = "The client '%d' closed the connection";

    private  Socket socket;
    private  int    num;

    public Server() {}
    public void setSocket(int num, Socket socket)
    {
        // Определение значений
        this.num    = num;
        this.socket = socket;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }
    public void run()
    {
        try {
            ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
            dos.flush();
            ObjectInputStream  dis = new ObjectInputStream (socket.getInputStream() );
            Object object;
            //User user;
            while(true) {
                // Ожидание сообщения от клиента
                object = dis.readObject();
                System.out.println("object come"+object);
                dos.writeUTF("Object get");
                dos.flush();
                String line= dis.readUTF();
                System.out.println(line);

                MainController mainController = new MainController(dos,dis);
                mainController.useCommand(object,line);

            }
        } catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}