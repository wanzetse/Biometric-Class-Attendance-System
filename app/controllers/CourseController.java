package controllers;

import models.Course;
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

public class CourseController extends Controller {
    private final FormFactory formFactory;
    private final MessagesApi messagesApi;
    private final AssetsFinder assetsFinder;

    @Inject
    public CourseController(FormFactory formFactory, MessagesApi messagesApi, AssetsFinder assetsFinder) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.assetsFinder = assetsFinder;
    }

    public Result adminIndex(Http.Request request){
        List<Course> courseList=Course.finder.all();
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Courses ");
        titles.add("Courses");
        titles.add(courseList.size()+" Registered Courses");
        User user=LecturersController.sessionUser(request);

        return ok(views.html.pageViews.courselist.render(titles,user,courseList,assetsFinder));
    }

    public Result add_EditCourse(Http.Request request){

        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Add/Edit Course ");
        titles.add("Add /Edit Courses");
        titles.add("Course Detail Form");
        Form<Course> courseForm=formFactory.form(Course.class);
        User user=LecturersController.sessionUser(request);


        return ok(views.html.formsviews.courseform.render(titles,courseForm,user,assetsFinder,
                messagesApi.preferred(request),request));
    }

    public Result add_EditCourseP(Http.Request request){
        Form<Course> courseForm=formFactory.form(Course.class).bindFromRequest(request);
        User user=LecturersController.sessionUser(request);
        if (courseForm.hasErrors()){
            List<String> titles=new ArrayList<>();
            titles.add("Admin | Laikipia University | Add/Edit Course ");
            titles.add("Add /Edit Courses");
            titles.add("Course Detail Form");
            return badRequest(views.html.formsviews.courseform.render(titles,courseForm,user,
                    assetsFinder,messagesApi.preferred(request),request));

        }
        Course course=courseForm.get();
        if(courseForm.field("id").value().get().isEmpty()){
            course.save();
        }else{
            course.update();
        }

        return redirect(routes.CourseController.add_EditCourse());
    }

    public Result editCourse(Http.Request request,String courseInitials){
        Course course=Course.finder.query().where().ieq("courseCode",courseInitials).findOne();
        if(course==null){
            return redirect(routes.CourseController.add_EditCourse());
        }
        Form<Course> courseForm=formFactory.form(Course.class);
        courseForm=courseForm.fill(course);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Add/Edit Course ");
        titles.add("Edit "+courseInitials);
        titles.add("Change "+courseInitials+" Details");
        User user=LecturersController.sessionUser(request);


        return ok(views.html.formsviews.courseform.render(titles,courseForm,user,
                assetsFinder,messagesApi.preferred(request),request));
    }
}
