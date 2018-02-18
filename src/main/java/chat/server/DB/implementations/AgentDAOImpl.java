package chat.server.DB.implementations;

import chat.Model.Agent;
import chat.server.DB.HibernateUtil;
import chat.server.DB.interfaces.AgentDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

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

    @Override
    public Agent find(String log, String pass,int port, String ip) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM Agent U where U.login=:log and U.pass=:pass");
        query.setParameter("log",log);
        query.setParameter("pass",pass);
        List<Agent> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            updatePort(log,pass,port,ip);
            markFree(log,pass);
            return result.get(0);
        }
        else return null;
    }

    @Override
    public Agent isFreeAgent() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM Agent U where U.isFree!=0");
        List<Agent> agents= query.list();
        session.getTransaction().commit();
        if(!agents.isEmpty()){
            return agents.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void updatePort(String log, String pass, int port, String ip) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Agent set port = :port, ip=:ip where login=:login and pass=:password");
        query.setParameter("port",port);
        query.setParameter("ip",ip);
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void markFree(String log, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Agent set isFree = 1 where login=:login and pass=:password");
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void markNotFree(String log, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE Agent set isFree = 0 where login=:login and pass=:password");
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public String getMessage(Agent agent) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U.message FROM Agent U where U.login=:log and U.pass=:pass");
        query.setParameter("log",agent.getLogin());
        query.setParameter("pass",agent.getPass());
        List<String> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }

    @Override
    public int getPort(Agent agent) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U.port FROM Agent U where U.login=:log and U.pass=:pass");
        query.setParameter("log",agent.getLogin());
        query.setParameter("pass",agent.getPass());
        List<Integer> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }
}
