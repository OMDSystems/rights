package controllers;

import models.*;
import play.*;
import play.mvc.*;
import java.util.*;

public abstract class AbstractController extends Controller {

    @Before
    static void setFormat() {
        request.format = "json";
    }

}

