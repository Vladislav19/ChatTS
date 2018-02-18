package chat.server;


import chat.Model.Agent;
import chat.Model.User;
import chat.server.Controllers.MainController;

import java.io.*;
import java.net.*;

public class Server extends Thread
{
    private  Socket socket;

    public Server() {}
    public void setSocket(int num, Socket socket)
    {
        int num1 = num;
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
                object = dis.readObject();
                System.out.println("object come"+object);
                dos.writeUTF("Object get");
                dos.flush();
                String line= dis.readUTF();

                MainController mainController = new MainController(dos,dis);
                mainController.useCommand(object,line);
            }
        } catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}