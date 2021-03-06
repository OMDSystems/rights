package models;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.Model;
import play.libs.Codec;

/**
 *
 * @author Marvin
 * @author Dario
 */
@Entity
public final class User extends Model{

    // specifies how long a session is valid in seconds
    private static long SESSION_DURATION = 30 * 60;

    public static User authenticate(String username, String password) throws AuthenticationFailedException{
        User user = User.find("byUsername", username).first();
        if (user == null || !user.getPassword().equals(Codec.hexSHA1(password))) {
            throw new AuthenticationFailedException("Username or password wrong.");
        }
        return user;
    }

    @ManyToOne
    @Required
    private UserGroup group;
    @Column(unique=true)
    private String username;
    private String password;
    private String sessionKey;
    private long sessionEnd;

    public User(String username, String password, UserGroup group) {
        this.group = group;
        this.username = username;
        this.password = Codec.hexSHA1(password);
        this.sessionEnd = 0;
    }

    public UserGroup getUserGroup() {
        return group;
    }

    public void setUserGroup(UserGroup group) {
        this.group = group;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Codec.hexSHA1(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> getOMDAuthInfo() {
        if(!hasActiveSession()) {
            startSession();
        }
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("omd_auth_key", sessionKey);
        long valid_for = sessionEnd - System.currentTimeMillis() / 1000;
        info.put("valid_for", valid_for > 0 ? valid_for : 0);
        return info;
    }

    private boolean hasActiveSession() {
        return sessionEnd - System.currentTimeMillis() / 1000 > 0;
    }

    private void startSession() {
        sessionKey = Codec.UUID();
        sessionEnd = System.currentTimeMillis() / 1000 + SESSION_DURATION;
        this.save();
    }

}
