package controllers;

import models.Attendance;
import models.Student;
import models.Unit;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StudentsController extends Controller {
    private final FormFactory formFactory;
    private final MessagesApi messagesApi;
    private final AssetsFinder assetsFinder;

    @Inject
    public StudentsController(FormFactory formFactory, MessagesApi messagesApi, AssetsFinder assetsFinder) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.assetsFinder = assetsFinder;
    }

    public Result adminIndex(Http.Request request){

        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University Portal | Students List ");
        titles.add("Students");
        List<Student> studentList=Student.finder.all();
        titles.add(studentList.size()+" Registered Students");

        return ok(views.html.pageViews.studentlistview.render(titles,LecturersController.sessionUser(request),studentList,assetsFinder));

    }

    public Result add_EditStudent(Http.Request request){
        Form<Student> studentForm=formFactory.form(Student.class);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikpia University Portal | Add Student");
        titles.add("Add / Edit Student");
        titles.add("Student Form Details");
        return ok(views.html.formsviews.studentform.render(titles,studentForm,LecturersController.sessionUser(request),assetsFinder,
                messagesApi.preferred(request),request));
    }
    public Result add_EditStudentP(Http.Request request){
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikpia University Portal | Add Student");
        titles.add("Add / Edit Student");
        titles.add("Student Form Details");
        Form<Student> studentForm=formFactory.form(Student.class).bindFromRequest(request);
        System.out.println(studentForm);
        if(studentForm.hasErrors()){
            return badRequest(views.html.formsviews.studentform.render(titles,studentForm,LecturersController.sessionUser(request),assetsFinder,
                    messagesApi.preferred(request),request));
        }
        Student student=studentForm.get();
        if(!studentForm.field("id").value().get().isEmpty()){

            student.update();
        }else {
            student.save();
        }
        return redirect(routes.StudentsController.add_EditStudent());
    }

    public Result editStudent(Http.Request request,String regno){
        regno=regno.replaceAll("_","/");
        Student std=Student.finder.query().where().ieq("reg_no",regno).findOne();
        if(std==null){
            return redirect(routes.StudentsController.add_EditStudent());
        }
        Form<Student> studentForm=formFactory.form(Student.class);
        studentForm=studentForm.fill(std);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikpia University Portal | Add Student");
        titles.add(" Edit Student / "+regno);
        titles.add("Student Form Details");

        return ok(views.html.formsviews.studentform.render(titles,studentForm,LecturersController.sessionUser(request),assetsFinder,
                messagesApi.preferred(request),request));

    }




    public static  Student studentFromEmail(String email){
        Student student=Student.finder.query().where().ieq("email",email).findOne();
        if(student==null){
            student=new Student();
        }
        return student;
    }
    public static List<Unit> studentUnitList(Student student){

        List<Unit> units=new ArrayList<>();
        List<Attendance> attendances=Attendance.finder.all();
        for(Attendance attendance:attendances){
            if(student.equals(attendance.getStudent())){
                Unit unit=attendance.getCls().getUnit();
                if(!units.contains(unit)){
                    units.add(unit);
                }
            }
        }
        return units;
    }

    public static List<Attendance> studentAttendances(Student student,Unit unit){
        List<Attendance> attendances=new ArrayList<>();
        for(Attendance attendance:Attendance.finder.all()){
            if(student.equals(attendance.getStudent())){
                if(unit.equals(attendance.getCls().getUnit())){
                    attendances.add(attendance);
                }
            }
        }
        return  attendances;
    }



}
