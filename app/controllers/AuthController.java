package controllers;

import forms.RegisterAdminForm;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.registerAdmin;

import javax.inject.Inject;

public class AuthController extends Controller {
    private FormFactory formFactory;
    private final AssetsFinder assetsFinder;
    private MessagesApi messagesApi;

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
        return ok("registered");
    }
}
