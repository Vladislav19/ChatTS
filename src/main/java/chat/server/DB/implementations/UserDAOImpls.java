package chat.server.DB.implementations;

import chat.Model.User;
import chat.server.DB.HibernateUtil;
import chat.server.DB.interfaces.UserDAO;
import org.hibernate.Session;

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
}
