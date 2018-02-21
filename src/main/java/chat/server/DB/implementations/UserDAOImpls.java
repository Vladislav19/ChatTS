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
    public User find(String log, String pass,int port, String ip) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",log);
        query.setParameter("pass",pass);
        List<User> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            markFree(log,pass,port,ip);
            return result.get(0);
        }else return null;
    }

    public void markFree(String log, String pass,int port, String ip){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set port = :port, isOnline = :online, ip=:ip where login=:login and pass=:password");
        query.setParameter("port",port);
        query.setParameter("online",1);
        query.setParameter("ip",ip);
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
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

    public User findFreeAgent(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.role=:role and U.isOnline=:online");
        query.setParameter("role","AGENT");
        query.setParameter("online",1);
        List<User> result = query.list();
        session.getTransaction().commit();
        if (!result.isEmpty()){
            return result.get(0);
        }
        else return null;
    }
    public void markNotFree(String log, String pass){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set  isOnline = :online where  login=:login and pass=:password");
        query.setParameter("online",0);
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }
    public void markNotFreeByPort(int port){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set isOnline = :online where  port=:port");
        query.setParameter("online",0);
        query.setParameter("port",port);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void markFreeByPort(int port) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set isOnline = :online where  port=:port");
        query.setParameter("online",1);
        query.setParameter("port",port);
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
