package controllers;

import forms.RegisterAdminForm;
import models.User;
import modules.HashP;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.registerAdmin;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class AuthController extends Controller {
    private final FormFactory formFactory;
    private final AssetsFinder assetsFinder;
    private final MessagesApi messagesApi;

    @Inject
    public AuthController(FormFactory formFactory,AssetsFinder assetsFinder,MessagesApi messagesApi){
        this.messagesApi=messagesApi;
        this.assetsFinder=assetsFinder;
        this.formFactory=formFactory;
    }

    public Result registerSystemAdmin(Http.Request request){
        Form<RegisterAdminForm> registerAdminFormForm=formFactory.form(RegisterAdminForm.class);
       return ok(registerAdmin.render("Register Admin",
               registerAdminFormForm,assetsFinder,request,
               messagesApi.preferred(request)));
    }

    public Result adminRegister(Http.Request request){
        Form<RegisterAdminForm> rform=formFactory.form(RegisterAdminForm.class).bindFromRequest(request);
        if (rform.hasErrors()){
            return badRequest(registerAdmin.render("Register Admin",
                    rform,assetsFinder,request,
                    messagesApi.preferred(request)));
        }
        RegisterAdminForm registerAdminForm=rform.get();
        User user=new User();
        user.setEmail(registerAdminForm.getEmail());
        user.setPassword(registerAdminForm.getPassword());
        user.setUsername(registerAdminForm.getUsername());
        user.setAdmin(true);
        user.save();
        return redirect(routes.AuthController.login());
    }

    public Result logout(Http.Request request){
        return redirect(routes.AuthController.login()).withNewSession();
    }

    public Result login(Http.Request request){
        return ok(views.html.login.render(assetsFinder,request));
    }
    public Result loginP(Http.Request request){
        DynamicForm form=formFactory.form().bindFromRequest(request);
        try{
            String username=form.get("username");
            String password=form.get("password");
            if(User.finder.query().findCount()<1){
                return redirect(routes.AuthController.registerSystemAdmin());
            }
            User user=User.finder.query().where().ieq("username",username).findOne();
            if(user==null){
                Map<String,String> flash=new HashMap<>();
                flash.put("error","Username Or Password Wrong!!!");
                return redirect(routes.AuthController.login()).withFlash(flash);
            }
            String usP=user.getPassword();
            if(HashP.checkP(password,usP)){
                Map<String,String> session=new HashMap<>();
                session.put("user",user.getUsername());
                return redirect(routes.HomeController.index()).withSession(session);
            }

        }catch (Exception exception){
            exception.printStackTrace();
            return redirect(routes.AuthController.login());
        }


        return ok("");
    }
}
