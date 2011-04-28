package models;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import play.db.jpa.Model;

/**
 *
 * @author Marvin
 */
@Entity
public class User extends Model{

    private static Map<String, String> users = new HashMap<String, String>();


    public static boolean isPairCorrect(String username, String password) throws UserNotFoundException{
        String _password = users.get(username);
        if (_password == null) {
            throw new UserNotFoundException();
        }
        return _password.equals(password);
    }

    private String username;

    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
