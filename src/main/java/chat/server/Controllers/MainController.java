package chat.server.Controllers;

import chat.Model.Agent;
import chat.Model.User;
import chat.server.DB.implementations.AgentDAOImpl;
import chat.server.DB.implementations.UserDAOImpls;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainController {

    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    public MainController(ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream) {
        this.objectInputStream=objectInputStream;
        this.objectOutputStream=objectOutputStream;
    }

    public void useCommand(Object object,String string) throws IOException {
        if(string.equals("RegUSER")){
            System.out.println("Use command save");
            UserDAOImpls userDAO = new UserDAOImpls();
            userDAO.save((User)object);
            objectOutputStream.writeUTF("Registration success");
            objectOutputStream.flush();
        }
        else if(string.equals("RegAGENT")){
            System.out.println("Use command save");
            AgentDAOImpl userDAO = new AgentDAOImpl();
            userDAO.save((Agent) object);
            objectOutputStream.writeUTF("Registration success");
            objectOutputStream.flush();
        }
    }
}
