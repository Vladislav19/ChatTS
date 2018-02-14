package chat.server.DB.implementations;

import chat.Model.User;
import chat.server.DB.HibernateUtil;
import chat.server.DB.interfaces.UserDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Владислав on 14.02.2018.
 */
public class UserDAOImpls implements UserDAO {
    @Override
    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public User find(String log, String pass,int port) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",log);
        query.setParameter("pass",pass);
        List<User> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            updatePort(log,pass,port);
            return result.get(0);
        }else return null;
    }

    @Override
    public void addMessage(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set message = :message where login=:login and pass=:password");
        query.setParameter("message",user.getMessage());
        query.setParameter("login",user.getLogin());
        query.setParameter("password",user.getPass());
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void updatePort(String log, String pass,int port) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set port = :port where login=:login and pass=:password");
        query.setParameter("port",port);
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public String getMessage(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U.message FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",user.getLogin());
        query.setParameter("pass",user.getPass());
        List<String> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }

    @Override
    public int getPort(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U.port FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",user.getLogin());
        query.setParameter("pass",user.getPass());
        List<Integer> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }

    @Override
    public User getUser(String log, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",log);
        query.setParameter("pass",pass);
        List<User> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }
}
