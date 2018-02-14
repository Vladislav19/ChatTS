package chat.server.DB.interfaces;

import chat.Model.User;

/**
 * Created by Владислав on 14.02.2018.
 */
public interface UserDAO {
    void save(User user);
    User find(String log,String pass, int port);
    void addMessage(User user);
    void updatePort(String log,String pass, int port);
    String getMessage(User user);
    int getPort(User user);
    User getUser(String log,String pass);
}
