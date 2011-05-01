package models;

import play.*;
import play.db.jpa.*;
import javax.persistence.*;
import play.data.validation.*;
import java.util.*;

/**
 *
 * @author Dario
 */

@Entity
public final class UserGroup extends Model {

    @Required
    @Column(unique=true)
    private String name;
    private String description;

    @OneToMany(mappedBy="group")
    private List<User> users;

    public UserGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<User>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

}

