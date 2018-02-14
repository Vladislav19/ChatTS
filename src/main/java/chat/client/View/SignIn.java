package chat.client.View;

import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class SignIn {
    public void doSignIn(){
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Type your login");
        String login = in.nextLine();
        System.out.println("Type your password");
        String password = in.nextLine();
    }
}
