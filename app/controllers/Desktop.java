package controllers;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Map;

public class Desktop extends Controller {
    FormFactory formFactory;

    @Inject
    public  Desktop(FormFactory formFactory){
        this.formFactory=formFactory;
    }

    public Result login(Http.Request request){
        DynamicForm dynamicForm=formFactory.form().bindFromRequest(request);
        String username=dynamicForm.get("username");
        String password=dynamicForm.get("password");
        return ok("Received\nUsername="+username+"\nPassword="+password);
    }
}
