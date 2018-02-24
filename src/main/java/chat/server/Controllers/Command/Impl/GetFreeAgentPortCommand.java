package chat.server.Controllers.Command.Impl;

import chat.Model.User;
import chat.server.Controllers.Command.Interface.Command;
import chat.server.DB.interfaces.UserDAO;
import chat.server.Logic.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 24.02.2018.
 */
public class GetFreeAgentPortCommand implements Command {
    Object object;
    UserDAO userDAO;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public GetFreeAgentPortCommand(Object object, UserDAO userDAO, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.object = object;
        this.userDAO = userDAO;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void execute() throws IOException, SQLException {
        MainLogic logic = new MainLogic(userDAO);
        String address = logic.searchFreeAgent((User)object);
        objectOutputStream.writeUTF(address);
        objectOutputStream.flush();
    }
}
