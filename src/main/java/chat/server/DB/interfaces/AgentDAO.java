package chat.server.DB.interfaces;

import chat.Model.Agent;

/**
 * Created by Владислав on 14.02.2018.
 */
public interface AgentDAO {
    void save(Agent agent);
    Agent find(String log,String pass,int port, String ip);
    Agent isFreeAgent();
    void updatePort(String log,String pass, int port, String ip);
    void markFree(String log,String pass);
    void markNotFree(String log,String pass);
    String getMessage(Agent agent);
    int getPort(Agent agent);
}
