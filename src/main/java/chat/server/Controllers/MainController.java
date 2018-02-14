package chat.server.Controllers;

import chat.Model.Agent;
import chat.Model.User;
import chat.server.DB.implementations.AgentDAOImpl;
import chat.server.DB.implementations.UserDAOImpls;
import chat.server.Logic.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainController {
    UserDAOImpls userDAO = new UserDAOImpls();
    AgentDAOImpl agentDAO = new AgentDAOImpl();
    MainLogic logic = new MainLogic();
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    public MainController(ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream) {
        this.objectInputStream=objectInputStream;
        this.objectOutputStream=objectOutputStream;
    }

    public void useCommand(Object object,String string) throws IOException {

        if(string.equals("RegUSER")){
            System.out.println("Use command save");
            userDAO.save((User)object);
            objectOutputStream.writeUTF("Registration success");
            objectOutputStream.flush();
        }

        else if(string.equals("RegAGENT")){
            System.out.println("Use command save");
            agentDAO.save((Agent) object);
            objectOutputStream.writeUTF("Registration success");
            objectOutputStream.flush();
        }

        else if(string.equals("Autorisation")){
            String str = (String)object;
            String[] mas = str.split(" ");
            String log = mas[0];
            String pass = mas[1];
            User user;Agent agent;
            int port = Integer.parseInt(mas[2]);
            System.out.println("Use command find employee");
            if((user = userDAO.find(log,pass,port))!=null){
                objectOutputStream.writeObject(user);
                objectOutputStream.flush();
            }
            else if((agent = agentDAO.find(log,pass,port))!=null){
                objectOutputStream.writeObject(agent);
                objectOutputStream.flush();
            }
            else {
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
            }
        }

        else if(string.equals("SendMessage")){
            System.out.println("Use command save message");
            userDAO.addMessage((User)object);
            logic.searchFreeAgent((User)object);
        }


    }
}
