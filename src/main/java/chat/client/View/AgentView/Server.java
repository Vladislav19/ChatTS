package chat.client.View.AgentView;


import chat.client.Client;

import java.io.*;
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            //TODO закрытие соединеия с текущим пользователем
        }
    });


    public void run()
    {

        try {
            ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream  dis = new ObjectInputStream (socket.getInputStream() );
            StringBuilder mes;
            thread.start();
            while(true) {
                String message = dis.readUTF();
                time=0;
                System.out.println("Message come: " + message);
                dos.writeUTF("Message get, wait, agent read u message");
                dos.flush();
                System.out.println("Please when you end type u things, type ;");
                mes= new StringBuilder();
                while (true){
                    mes.append(in.nextLine()+"\n");
                    if(mes.charAt(mes.length()-2)==';'){
                        break;
                    }
                }
                dos.writeUTF("Response from agent: " + mes);
                dos.flush();
            }
        }catch(EOFException e) {
            try {
                socket.close();
            } catch (IOException e1) {
            }
            Client.menu();
        }catch(Exception e) {
        }
    }
}