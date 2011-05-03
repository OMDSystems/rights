package controllers;

import models.*;
import play.*;
import play.mvc.*;
import java.util.*;
import javax.persistence.*;

public class Users extends AbstractController {

    public static void create(String username, String password, long group_id) {
        try {
            UserGroup group = UserGroup.findById(group_id);
            notFoundIfNull(group, "Group with id " + group_id + " does not exist.");
            User user = new User(username, password, group);
            user.save();
            renderTemplate("Users/_user.json", user);
        } catch(PersistenceException e) {
            badRequest();
        }
    }

    public static void authenticate(String username, String password) throws AuthenticationFailedException {
        try {
            User user = User.authenticate(username, password);
            renderJSON(user.getOMDAuthInfo());
        } catch (AuthenticationFailedException e) {
            badRequest();
        }
    }

    public static void authorize(String token, String uri) {
        User user = User.find("bySessionKey", token).first();
        if(user == null) {
            unauthorized();
        } else {
            if((user.getUserGroup().getName().equals("parents") && uri.equals("/queue/position"))
                || user.getUserGroup().getName().equals("admins")) {
                ok();
            } else {
                renderJSON(uri);
            }
        }
    }
}

