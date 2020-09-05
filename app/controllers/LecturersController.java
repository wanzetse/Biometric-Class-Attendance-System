package controllers;

import models.Lecturer;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LecturersController extends Controller {
    private final FormFactory formFactory;
    private final MessagesApi messagesApi;
    private final AssetsFinder assetsFinder;
    @Inject
    public LecturersController(FormFactory formFactory, MessagesApi messagesApi, AssetsFinder assetsFinder) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.assetsFinder = assetsFinder;
    }


    public Result createLecturerForm(Http.Request request){
        Form<Lecturer> lecturerForm=formFactory.form(Lecturer.class);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikpia University Portal | Add Lecturer");
        titles.add("Add / Edit Lecturer");
        titles.add("Lecturer Form Details");
        return ok(views.html.formsviews.lecturerform.render(titles,lecturerForm,sessionUser(request),
                assetsFinder,messagesApi.preferred(request),request));
    }


    public Result lecturerFormP(Http.Request request){
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikpia University Portal | Add Lecturer");
        titles.add("Add / Edit Lecturer");
        titles.add("Lecturer Form Details");
        Form<Lecturer> lecturerForm=formFactory.form(Lecturer.class).bindFromRequest(request);

        if (lecturerForm.hasErrors()){
            System.out.println(lecturerForm.errors());

            return badRequest(views.html.formsviews.lecturerform.render(titles,lecturerForm,sessionUser(request),
                    assetsFinder,messagesApi.preferred(request),request));
        }
        Lecturer lecturer=lecturerForm.get();
        if(!lecturerForm.field("id").value().get().isEmpty()){
            lecturer.update();
        }else{
            lecturer.save();
        }
        return redirect(routes.LecturersController.createLecturerForm());
    }

    public Result editLecturerForm(Http.Request request,String work_Id){
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikpia University Portal | Add Lecturer");
        titles.add("Add / Edit Lecturer");
        titles.add("Lecturer Form Details");
        Lecturer lecturer=Lecturer.finder.query().where().ieq("work_id",work_Id).findOne();
        if (lecturer==null){
            return redirect(routes.LecturersController.createLecturerForm());
        }
        Form<Lecturer> lecturerForm=formFactory.form(Lecturer.class);
        lecturerForm=lecturerForm.fill(lecturer);
        Map<String,String> flash=new HashMap<>();
        flash.put("success","Lecturer Saved Successfully");
        return ok(views.html.formsviews.lecturerform.render(titles,lecturerForm,sessionUser(request),
                assetsFinder,messagesApi.preferred(request),request)).withFlash(flash);
    }

    public static User sessionUser(Http.Request request){
        User user=User.finder.query().where().ieq("username",request.session().get("user").orElse("")).findOne();
        if(user==null){
            user=new User();
        }
        return  user;
    }
}
