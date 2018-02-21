package chat.server.Logic;

import chat.Model.User;
import chat.server.DB.H2.UserDAOImplsH2;
import chat.server.DB.implementations.UserDAOImpls;
import chat.server.DB.interfaces.UserDAO;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainLogic {

    UserDAO userDAO ;
    int db;

    public MainLogic(int db) {
        this.db = db;
    }

    public int searchFreeAgent(User user){
        if(db==1){
            userDAO = new UserDAOImplsH2();
        }
        if(db==2){
            userDAO = new UserDAOImpls();
        }
        User freeAgent = userDAO.findFreeAgent();
        if(freeAgent!=null){
            userDAO.markNotFree(freeAgent.getLogin(),freeAgent.getPass());
            return freeAgent.getPort();
        }else{
            return -1;
        }
    }

}
