package chat.client;


import chat.client.View.Registration;
import chat.client.View.SignIn;

import java.io.IOException;
import java.util.Scanner;

public class Client
{
    public static void main(String[] ar) {
        menu();
    }

    public static  void menu(){
        Scanner in = new Scanner(System.in);
        System.out.println("Hello, please type u choose");
        System.out.println("1) Registration");
        System.out.println("2) Sign in");
        System.out.println("3) Exit");
        try {
            trace(in.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void trace(String str) throws IOException {
        Scanner in = new Scanner(System.in);
        switch (str){
            case "1": Registration reg = new Registration();reg.doRegistr();break;
            case "2": SignIn signIn = new SignIn();signIn.doSignIn();break;
            case "3": System.exit(0);
            default:
                System.out.println("You misclick, retype");trace(in.nextLine());
        }
    }
}
