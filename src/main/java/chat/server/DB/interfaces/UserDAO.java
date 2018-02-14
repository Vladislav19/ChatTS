package chat.server.DB.interfaces;

import chat.Model.User;

/**
 * Created by Владислав on 14.02.2018.
 */
public interface UserDAO {
    void save(User user);
}
