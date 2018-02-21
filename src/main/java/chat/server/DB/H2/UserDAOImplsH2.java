package chat.server.DB.H2;

import chat.Model.User;
import chat.server.DB.interfaces.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 21.02.2018.
 */
public class UserDAOImplsH2 implements UserDAO {

    private static final String driverName = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:chatDB;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1";
    private static final String username = "test";
    private static final String password = "test";

    private static final String tableScript = "create table users"+
            "("+
            "    id int auto_increment"+
            " primary key,"+
            " login varchar(20) not null,"+
            " pass varchar(100) not null,"+
            " port int null,"+
            " ip varchar(20) null,"+
            " role varchar(20) not null, "+
            "isOnline int(1) default '0' not null"+
            ");";

    int i=0;

    public void createTable(){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(URL,username,password);
            Statement statement = connection.createStatement();
            statement.execute(tableScript);
            System.out.println("Table created successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection initConnection(){
        if(i==0){
            createTable();
        }
        i++;
        Connection conn = null;
        try {
            Class.forName(driverName).newInstance();
             conn = DriverManager.getConnection(URL, username, password);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return conn;
    }

    @Override
    public void save(User user)  {
        Connection connection = initConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO users (login, pass, role, isOnline) VALUES (?,?,?,?)");
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPass());
            statement.setString(3,user.getRole());
            statement.setInt(4,user.getIsOnline());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User find(String log, String pass, int port, String ip) {
        User user;
        List<User> users = new ArrayList<>();
        try {
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login=? and pass=? ");
            statement.setString(1,log);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPass(resultSet.getString("pass"));
                user.setPort(resultSet.getInt("port"));
                user.setRole(resultSet.getString("role"));
                user.setIp(resultSet.getString("ip"));
                user.setIsOnline(resultSet.getInt("isOnline"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!users.isEmpty()){
            markFree(log,pass,port,ip);
            return users.get(0);
        }
        else return null;
    }

    @Override
    public User findFreeAgent() {
        User user;
        List<User> users = new ArrayList<>();
        try {
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE isOnline = 1 ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPass(resultSet.getString("pass"));
                user.setPort(resultSet.getInt("port"));
                user.setRole(resultSet.getString("role"));
                user.setIp(resultSet.getString("ip"));
                user.setIsOnline(resultSet.getInt("isOnline"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!users.isEmpty()){
            return users.get(0);
        }
        else return null;
    }

    @Override
    public void markFree(String log, String pass, int port, String ip) {
        try{
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET port = ?, isOnline = 1, ip=? where login=? and pass=?");
            statement.setInt(1,port);
            statement.setString(2,ip);
            statement.setString(3,log);
            statement.setString(4,pass);
            statement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void markNotFree(String log, String pass) {
        try{
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET isOnline = 0 where login=? and pass=?");
            statement.setString(1,log);
            statement.setString(2,pass);
            statement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void markNotFreeByPort(int port) {
        try{
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET isOnline = 0 where port = ?");
            statement.setInt(1,port);
            statement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void markFreeByPort(int port) {
        try{
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET isOnline = 1 where port = ?");
            statement.setInt(1,port);
            statement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public User getUser(String log, String pass) {
        User user;
        List<User> users = new ArrayList<>();
        try {
            Connection connection = initConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login=? and pass=? ");
            statement.setString(1,log);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPass(resultSet.getString("pass"));
                user.setPort(resultSet.getInt("port"));
                user.setRole(resultSet.getString("role"));
                user.setIp(resultSet.getString("ip"));
                user.setIsOnline(resultSet.getInt("isOnline"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!users.isEmpty()){
            return users.get(0);
        }
        else return null;
    }
}
