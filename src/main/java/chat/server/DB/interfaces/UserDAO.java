package chat.server.DB.interfaces;

import chat.Model.User;

import java.sql.SQLException;

/**
 * Created by Владислав on 14.02.2018.
 */
public interface UserDAO {
    void save(User user) throws SQLException;
    User find(String log,String pass, int port, String ip);
    User findFreeAgent();
    void markFree(String log, String pass,int port, String ip);
    void markNotFree(String log, String pass);
    void markNotFreeByPort(int port);
    void markFreeByPort(int port);
    User getUser(String log,String pass);
}
