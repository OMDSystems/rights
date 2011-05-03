package controllers;

import models.*;
import play.*;
import play.mvc.*;
import java.util.*;
import javax.persistence.*;

public class Groups extends AbstractController {

    public static void show(long id) {
        UserGroup group = UserGroup.findById(id);
        notFoundIfNull(group);
        renderTemplate("Groups/_group.json", group);
    }

    public static void create(String name, String description) {
        try {
            UserGroup group = new UserGroup(name, description);
            group.save();
            renderTemplate("Groups/_group.json", group);
        } catch(PersistenceException e) {
            badRequest();
        }
    }

}

