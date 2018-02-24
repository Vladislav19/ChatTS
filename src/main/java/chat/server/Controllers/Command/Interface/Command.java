package chat.server.Controllers.Command.Interface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Владислав on 24.02.2018.
 */
public interface Command {
    void execute() throws IOException, SQLException;
}
