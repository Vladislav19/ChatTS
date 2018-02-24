package chat.server.Logic;

import chat.Model.User;
import chat.server.DB.interfaces.UserDAO;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainLogic {

    UserDAO userDAO ;

    public MainLogic(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String searchFreeAgent(User user){
        User freeAgent = userDAO.findFreeAgent();
        if(freeAgent!=null){
            userDAO.markNotFree(freeAgent.getLogin(),freeAgent.getPass());
            String result = freeAgent.getIp()+" "+freeAgent.getPort();
            return result;
        }else{
            return 0+" "+"-1";
        }
    }

}
