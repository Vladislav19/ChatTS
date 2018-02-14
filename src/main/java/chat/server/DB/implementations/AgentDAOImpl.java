package chat.server.DB.implementations;

import chat.Model.Agent;
import chat.server.DB.HibernateUtil;
import chat.server.DB.interfaces.AgentDAO;
import org.hibernate.Session;

/**
 * Created by Владислав on 14.02.2018.
 */
public class AgentDAOImpl implements AgentDAO {
    @Override
    public void save(Agent agent) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(agent);
        session.getTransaction().commit();
    }
}
