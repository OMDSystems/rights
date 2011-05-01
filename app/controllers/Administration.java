package controllers;

import play.mvc.Controller;
import models.*;

import java.util.*;
import javax.persistence.PersistenceException;

/**
 *
 * @author Marvin
 * @author Dario
 */
public class Administration extends Controller{

    public static void addUser(String username, String password, long group_id) {
        try {
            UserGroup group = UserGroup.findById(group_id);
            User user = new User(username, password, group);
            user.save();
            renderTemplate("Administration/_user.json", user);
        } catch(PersistenceException e) {
            badRequest();
        }
    }

    public static void getGroup(long id) {
        UserGroup group = UserGroup.findById(id);
        notFoundIfNull(group);
        renderTemplate("Administration/_group.json", group);
    }

    public static void authenticateUser() throws AuthenticationFailedException {
        try {
            User user = User.authenticate(params.get("username"), params.get("password"));
            renderJSON(user.getOMDAuthInfo());
        } catch (AuthenticationFailedException e) {
            badRequest();
        }
    }

    public static void addGroup() {
        UserGroup group = new UserGroup(params.get("name"), params.get("description"));
        group.save();
        renderTemplate("Administration/_group.json", group);
    }

}
