package chat.server.Logic;

import chat.Model.Agent;
import chat.Model.User;
import chat.server.DB.implementations.AgentDAOImpl;
import chat.server.DB.implementations.UserDAOImpls;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainLogic {

    UserDAOImpls userDAO = new UserDAOImpls();
    AgentDAOImpl agentDAO = new AgentDAOImpl();


    public void searchFreeAgent(User user){
        Agent freeAgent = agentDAO.isFreeAgent();
        if(freeAgent!=null){
            agentDAO.markNotFree(freeAgent.getLogin(),freeAgent.getPass());
            int portAgent = agentDAO.getPort(freeAgent);
            try {

                Socket socket = new Socket("127.0.0.1",portAgent);
                ObjectInputStream objectInputStream = (ObjectInputStream) socket.getInputStream();
                ObjectOutputStream objectOutputStream = (ObjectOutputStream)socket.getOutputStream();
                objectOutputStream.flush();
                objectOutputStream.writeUTF(userDAO.getMessage(user));
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }

}
