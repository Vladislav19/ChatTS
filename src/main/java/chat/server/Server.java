package chat.server;


import chat.server.Controllers.MainController;

import java.io.*;
import java.net.*;

public class Server extends Thread
{
    private  Socket socket;
    int db;
    public Server(int db) {this.db=db;}

    public void setSocket(int num, Socket socket)
    {
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
            MainController mainController = new MainController(db,dos,dis);
            while(true) {
                object = dis.readObject();
                String line= dis.readUTF();
                mainController.useCommand(object,line);
            }
        }
        catch (EOFException ex){

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}