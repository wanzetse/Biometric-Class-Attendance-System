package controllers.Auth;

import controllers.routes;

import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import java.util.Optional;

public class Secured extends Security.Authenticator {

    @Override
    public Optional<String> getUsername(Http.Request req) {
       String username="";

            username=req.session().get("user").orElse("");
            User user=User.finder.query().where().ieq("username",username).findOne();
            if(user!=null){
                return Optional.of(username);
            }
            else {
                return Optional.ofNullable((null));
            }
    }

    @Override
    public Result onUnauthorized(Http.Request req) {

       return redirect(routes.AuthController.login()).
                flashing("danger",  "You need to login before access the application.");
    }
}