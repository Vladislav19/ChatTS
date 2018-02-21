package chat.server.Controllers;

import chat.Model.User;
import chat.server.DB.H2.UserDAOImplsH2;
import chat.server.DB.implementations.UserDAOImpls;
import chat.server.DB.interfaces.UserDAO;
import chat.server.Logic.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainController {

    UserDAO userDAO = null;

    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    int db;

    public MainController(int db,ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream) {
        this.db=db;
        this.objectInputStream=objectInputStream;
        this.objectOutputStream=objectOutputStream;
    }

    public void useCommand(Object object,String string) throws IOException {
        if(db==1){
            userDAO = new UserDAOImplsH2();
        }
        if(db==2){
            userDAO = new UserDAOImpls();
        }

        if(string.equals("Registration")){
            try {
                userDAO.save((User)object);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            objectOutputStream.writeUTF("Registration success");
            objectOutputStream.flush();
        }

        else if(string.equals("Autorisation")){
            String str = (String)object;
            String[] mas = str.split(" ");
            String log = mas[0];
            String pass = mas[1];
            User user;
            int port = Integer.parseInt(mas[2]);
            String ip = mas[3];
            if((user = userDAO.find(log,pass,port,ip.replaceAll("/","")))!=null){
                objectOutputStream.writeObject(user);
                objectOutputStream.flush();
            }
            else {
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
            }
        }

        else if(string.equals("GetFreeAgentPort")){
            MainLogic logic = new MainLogic(db);
            int port = logic.searchFreeAgent((User)object);
            objectOutputStream.writeInt(port);
            objectOutputStream.flush();
        }

        else if(string.equals("AgentIsNotActive")){
            int port = (Integer)object;
            userDAO.markNotFreeByPort(port);
        }

        else if(string.equals("MarkFree")){
            int port = (Integer)object;
            userDAO.markFreeByPort(port);
        }


    }
}
