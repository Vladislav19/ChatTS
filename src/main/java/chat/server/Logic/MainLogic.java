package chat.server.Logic;

import chat.Model.Agent;
import chat.Model.User;
import chat.server.DB.implementations.AgentDAOImpl;
import chat.server.DB.implementations.UserDAOImpls;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainLogic {

    UserDAOImpls userDAO = new UserDAOImpls();
    AgentDAOImpl agentDAO = new AgentDAOImpl();

    public int searchFreeAgent(User user){
        Agent freeAgent = agentDAO.isFreeAgent();
        if(freeAgent!=null){
            agentDAO.markNotFree(freeAgent.getLogin(),freeAgent.getPass());
            return freeAgent.getPort();
        }else{
            return -1;
        }
    }

}
