package controllers;

import play.mvc.Controller;
import models.*;

/**
 *
 * @author Marvin
 */
public class Administration extends Controller{

    public static void addUser(){
        User user = new User(params.get("username"), params.get("password"));
        user.save();
        renderJSON(user);
    }

    public static void authenticateUser() throws AuthenticationFailedException {
        User user = User.authenticate(params.get("username"), params.get("password"));
        renderJSON(user.getOMDAuthInfo());
    }

}
