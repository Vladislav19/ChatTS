package chat.client.View.AgentView;


import chat.client.Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread
{
    private  Socket socket;

    Scanner in = new Scanner(System.in);

    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    public Server(){
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public long time;
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(time<60){
                time++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //TODO закрытие соединеия с текущим пользователем
        }
    });
    public void run()
    {
        try {
            ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
            dos.flush();
            ObjectInputStream  dis = new ObjectInputStream (socket.getInputStream() );
            thread.start();
            while(true) {
                String message = dis.readUTF();
                time=0;
                System.out.println("Message come: " + message);
                dos.writeUTF("Message get, wait, agent read u message");
                dos.flush();
                dos.writeUTF("Response from agent: " + in.nextLine());
                dos.flush();
            }
        }catch(EOFException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Client.menu();
        }catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}