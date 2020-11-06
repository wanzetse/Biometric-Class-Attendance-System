package controllers;

import models.Attendance;
import models.Cls;
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
        return redirect(routes.StudentsController.adminIndex());
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
        System.out.println(student.getEmail());
        System.out.println(student.getReg_no());

        return student;
    }
    public static List<Unit> studentUnitList(Student student){

        List<Unit> units=new ArrayList<>();
        List<Attendance> attendances=Attendance.finder.all();
        for(Attendance attendance:attendances){
            if(student.equals(attendance.getStudent())){
                Unit unit=attendance.getCls().getUnit();
                System.out.println("Found something");
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

    public static List<Cls> clsList(Unit unit){
        List<Cls> clsList=unit.getClasses();
        return clsList;
    }
    public static  int percentage(Unit unit,List<Attendance> attendances,Student student){
        double per1=unit.getClasses().size();

        List<Attendance> attendances1=new ArrayList<>();
        for (Attendance attendance:attendances){
            if(attendance.getCls().getUnit().equals(unit)){
                attendances1.add(attendance);
            }
        }
        double per_2=attendances1.size();
        double p=(per_2/per1)*100.0;
        return (int) p;

    }


public static String pass_or_fail(int per){

        if(per>=75){
            return "Passed";
        }
        return "Failed";
}

}
