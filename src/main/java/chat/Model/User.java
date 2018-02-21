package chat.Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Владислав on 14.02.2018.
 */
@Entity
public class User implements Serializable{
    private int id;
    private String login;
    private String pass;
    private Integer port;
    private String ip;
    private String role;
    private int isOnline;

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "pass")
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isOnline != user.isOnline) return false;
        if (!login.equals(user.login)) return false;
        if (!pass.equals(user.pass)) return false;
        if (!port.equals(user.port)) return false;
        if (!ip.equals(user.ip)) return false;
        return role.equals(user.role);
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "port")
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "isOnline")
    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(byte isOnline) {
        this.isOnline = isOnline;
    }
}
