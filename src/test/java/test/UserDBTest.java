package test;

import chat.Model.User;
import chat.server.DB.implementations.UserDAOImpls;
import chat.server.DB.implementations.UserDAOImplsH2;
import chat.server.DB.interfaces.UserDAO;
import config.DBUnitConfig;
import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Владислав on 24.02.2018.
 */
public class UserDBTest extends DBUnitConfig {

    private UserDAO userDAO = new UserDAOImpls();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("person-data.xml"));

        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    public UserDBTest(String name) {
        super(name);
    }


    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setLogin("Ragneda");
        user.setPass("5d93ceb70e2bf5daa84ec3d0cd2c731a");
        user.setRole("USER");

        userDAO.save(user);

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("person-data-save.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "user", ignore);
    }

    @Test
    public void testMarkFreeByPort() throws SQLException {
        User user = new User();
        user.setLogin("Aqva");
        user.setPass("qwer1234");
        user.setRole("AGENT");
        user.setIsOnline(0);
        user.setPort(34566);

        userDAO.save(user);
        userDAO.markFreeByPort(user.getPort());

        Assert.assertEquals(userDAO.find(user.getLogin(),user.getPass(),user.getPort(),user.getIp()).getIsOnline(),1);
    }

    @Test
    public void testFind() throws Exception {
        User user = new User();
        user.setLogin("Aqva");
        user.setPass("qwer1234");
        user.setRole("USER");
        user.setIsOnline(0);
        user.setPort(34566);

        userDAO.save(user);

        Assert.assertEquals(userDAO.find(user.getLogin(),user.getPass(),user.getPort(),user.getIp()).getLogin(),user.getLogin());
        Assert.assertEquals(userDAO.find(user.getLogin(),user.getPass(),user.getPort(),user.getIp()).getPass(),user.getPass());
        Assert.assertEquals(userDAO.find(user.getLogin(),user.getPass(),user.getPort(),user.getIp()).getRole(),user.getRole());
        Assert.assertEquals(userDAO.find(user.getLogin(),user.getPass(),user.getPort(),user.getIp()).getPort(),user.getPort());
    }


}
