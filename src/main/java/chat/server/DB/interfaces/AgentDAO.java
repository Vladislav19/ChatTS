package chat.server.DB.interfaces;

import chat.Model.Agent;

/**
 * Created by Владислав on 14.02.2018.
 */
public interface AgentDAO {
    void save(Agent agent);
}
