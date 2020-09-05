package controllers;

import models.LectureHall;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class HallsController extends Controller {
    private final FormFactory formFactory;
    private final AssetsFinder assetsFinder;
    private final MessagesApi messagesApi;

    @Inject
    public HallsController(FormFactory formFactory, AssetsFinder assetsFinder, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.assetsFinder = assetsFinder;
        this.messagesApi = messagesApi;
    }

    public Result adminIndex(Http.Request request){
        List<LectureHall> lectureHalls=LectureHall.finder.all();
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Lecture Halls ");
        titles.add("Lecture Halls");
        titles.add(lectureHalls.size()+" Registered Lecture Halls");
        User user=LecturersController.sessionUser(request);

        return ok(views.html.pageViews.hallslistview.render(titles,user,lectureHalls,
                assetsFinder));
    }

    public Result add_EditHall(Http.Request request){
        Form<LectureHall> lectureHallForm=formFactory.form(LectureHall.class);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Add /Edit Lecture Hall ");
        titles.add("Add / Edit Lecture Hall");
        titles.add(" Lecture Hall Form Details");
        User user=LecturersController.sessionUser(request);

        return ok(views.html.formsviews.hallsForm.render(titles,lectureHallForm,user,
                assetsFinder,messagesApi.preferred(request),request));

    }

    public Result add_EditHallP(Http.Request request){
        Form<LectureHall> hallForm=formFactory.form(LectureHall.class).bindFromRequest(request);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University| Add / Edit Hall ");
        titles.add("Add /Edit Hall");
        titles.add("Hall Form Details");
        User user=LecturersController.sessionUser(request);

        if(hallForm.hasErrors()){
            System.out.println("\n\n"+hallForm+"\n\n");
            return badRequest(views.html.formsviews.hallsForm.render(titles,hallForm,user,
                    assetsFinder,messagesApi.preferred(request),request));
        }
        LectureHall lectureHall=hallForm.get();
        if(hallForm.field("id").value().get().isEmpty()){
            lectureHall.save();
        }else{
            lectureHall.update();
        }
        return redirect(routes.HallsController.add_EditHall());
    }

    public Result editHall(Http.Request request,String h_name){
        Form<LectureHall> hallForm=formFactory.form(LectureHall.class);
        LectureHall lectureHall=LectureHall.finder.query().where().ieq("room_name",h_name).findOne();
        if (lectureHall==null){
            return  redirect(routes.HallsController.add_EditHall());
        }
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University| Add / Edit Hall ");
        titles.add("Edit "+h_name);
        titles.add("Change "+h_name+" Details");
        User user=LecturersController.sessionUser(request);
        hallForm=hallForm.fill(lectureHall);

        return ok(views.html.formsviews.hallsForm.render(titles,hallForm,user,
                assetsFinder,messagesApi.preferred(request),request));
    }
}
