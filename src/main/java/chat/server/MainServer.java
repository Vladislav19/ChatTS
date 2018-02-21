package chat.server;

import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainServer {
    public static void main(String[] ar)
    {
        int db = chooseDB();
        ServerConnetion serverConnetion = new ServerConnetion(db);
        serverConnetion.startServer();
    }

    public static int chooseDB(){
        Scanner in = new Scanner(System.in);
        System.out.println("Type 1 if you use H2 and 2 if you use MySQL");
        int choose = in.nextInt();
        if(choose!=1 & choose!=2){
            System.out.println("Please type 1 or 2");
            chooseDB();
        }
        return choose;
    }
}
