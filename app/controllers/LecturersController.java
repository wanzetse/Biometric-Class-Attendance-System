package controllers;

import forms.ClassCreateForm;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.math.Integral;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
        return redirect(routes.HallsController.adminIndex());
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

    public CompletionStage<Result> currentClass(Http.Request request,Integer lec_id){
        try{
      Lecturer lecturer=Lecturer.finder.byId(lec_id);
      Date today=new Date();
        DateFormat dateFormat=new SimpleDateFormat("E, dd MMM yyyy");
        String date=dateFormat.format(today);
      List<Cls>  classes=Cls.finder.query().where().eq("lecturer",lecturer).findList();
      for(Cls cl:classes){
          if(cl.isOngoing()){
              String response=cl.getUnit().getUnitCode()+"\n" +
                      cl.getLectureHall().getRoom_name()+"\n" +
                      cl.attendanceList().size()+"\n" +
                      cl.getStart_time()+"\nBy " +cl.getLecturer().getTitle()+" "+
                      cl.getLecturer().fullName();
              return CompletableFuture.completedFuture(ok(response).withHeader("class-id",cl.getId()+""));
          }

      }}catch (Exception e){
        e.printStackTrace();
            return CompletableFuture.completedFuture(ok("").withHeader("class-id","No Class"));
        }
      return CompletableFuture.completedFuture(ok("").withHeader("class-id","No Class"));
    }


    public Result createClassForm(Http.Request request,Integer lec_id){
        List<String> titles=new ArrayList<>();
        User user=sessionUser(request);
        titles.add("Laikipia University | Staff Portal");
        titles.add("Create Class");
        titles.add("Class Details Form");
        Form<ClassCreateForm> classForm=formFactory.form(ClassCreateForm.class);
        Lecturer lecturer=Lecturer.finder.byId(lec_id);
        ClassCreateForm clsForm=new ClassCreateForm();
        clsForm.setLecturer(lecturer);
        classForm=classForm.fill(clsForm);
        return ok(views.html.formsviews.classForm.render(titles,classForm,user,assetsFinder,messagesApi.preferred(request),request));
    }

    public Result createClassFormP(Http.Request request,Integer id){
        List<String> titles=new ArrayList<>();
        User user=sessionUser(request);
        titles.add("Laikipia University | Staff Portal");
        titles.add("Create Class");
        titles.add("Class Details Form");
        Form<ClassCreateForm> classForm=formFactory.form(ClassCreateForm.class).bindFromRequest(request);
        if (classForm.hasErrors()){
            System.out.println(classForm);
            return badRequest(views.html.formsviews.classForm.render(titles,classForm,user,assetsFinder,messagesApi.preferred(request),request));
        }
        try{
            ClassCreateForm classCreateForm=classForm.get();
            Cls cls=new Cls();
            Date date=new Date();
            Instant newDate=date.toInstant().plus(Duration.ofSeconds((long) classCreateForm.getDuration()*3600));
            Date endDate=Date.from(newDate);
            DateFormat dateFormat=new SimpleDateFormat("E, dd MMM yyyy");
            DateFormat dateFormat2=new SimpleDateFormat("HH:mm:ss");
            cls.setDate(dateFormat.format(date));
            cls.setStart_time(dateFormat2.format(date));
            cls.setEnd_time(dateFormat2.format(endDate));
            cls.setLectureHall(classCreateForm.getLectureHall());
            cls.setDuration(classCreateForm.getDuration());
            cls.setLecturer(classCreateForm.getLecturer());
            cls.setUnit(classCreateForm.getUnit());
            cls.save();

        }catch (Exception e){
            e.printStackTrace();
        }

        return redirect(routes.LecturersController.classesIndex(id));
    }

    public Result classesIndex(Http.Request request,Integer lec_id){
        Lecturer lecturer=Lecturer.finder.byId(lec_id);
        List<Cls> classes=new ArrayList<>();
        for(Cls cl: Cls.finder.all()){
            if(cl.getLecturer().equals(lecturer)){
                classes.add(cl);
            }
        }
        User user=sessionUser(request);
        List<String> titles=new ArrayList<>();
        titles.add("Laikipia University Staff Portal| Classes");
        titles.add("Classes");
        titles.add("List of all Your Classes");

        return ok(views.html.pageViews.lectureClassesV.render(titles,user,classes,assetsFinder));

    }

    public Result endClass(Integer lec_id,Integer class_id){
        Cls cls=Cls.finder.byId(class_id);
        try{
            DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
            String end_time=dateFormat.format(new Date());
            cls.setEnd_time(end_time);
            cls.update();
        }catch (Exception e){
            e.printStackTrace();
        }

        return redirect(routes.LecturersController.classesIndex(lec_id));
    }

    public Result attendancePerClass(Http.Request request, Integer lec_id, Integer class_id){
        List<String> titles=new ArrayList<>();
        User user=sessionUser(request);
        Cls cls=Cls.finder.byId(class_id);
        titles.add("LU | Staff Portal | "+cls.getUnit().getUnitCode()+" Attendances");
        titles.add("Attendances For Unit "+cls.getUnit().getUnitCode());

        List<Attendance> attendances=cls.attendanceList();
        Lecturer lecturer=Lecturer.finder.byId(lec_id);
        List<Attendance> attendances1=new ArrayList<>();
        for(Attendance attendance:attendances){
            if (attendance.getCls().getLecturer().equals(lecturer)){
                attendances1.add(attendance);
            }
        }
        titles.add(attendances.size()+" Students  Attended Class Held on "+cls.getDate()+" from "+cls.getStart_time()+" to "+cls.getEnd_time());
        return ok(views.html.pageViews.attendancesView.render(titles,user,attendances1,assetsFinder));
    }
    public Result classesPerUnit(Http.Request request,Integer lec_id,Integer unit_id){
        Unit unit=Unit.finder.byId(unit_id);
        Lecturer lecturer=Lecturer.finder.byId(lec_id);
        List<Cls> classes=new ArrayList<>();
        for(Cls cl: Cls.finder.all()){
            if(cl.getLecturer().equals(lecturer)){
                if(cl.getUnit().equals(unit)) {
                    classes.add(cl);
                }
            }
        }
        User user=sessionUser(request);
        List<String> titles=new ArrayList<>();
        titles.add("Laikipia University Staff Portal| Classes");
        titles.add("Classes For Unit("+unit.getUnitCode()+")");
        titles.add(classes.size()+" Classes For "+unit.getUnitCode());

        return ok(views.html.pageViews.lectureClassesV.render(titles,user,classes,assetsFinder));

    }
    public Result attendancePerUnit(Http.Request request,Integer lec_id,Integer unit_id){

        List<Map<String,String>> atMaps=new ArrayList<>();
        String year="";
        try{
            DateFormat dateFormat=new SimpleDateFormat("yyyy");
            year=dateFormat.format(new Date());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<String> titles=new ArrayList<>();
        titles.add("Lu Portal| Unit Attendance");

        Unit unit=Unit.finder.byId(unit_id);
        titles.add("Unit Attendance For Unit ("+unit.getUnitCode()+") Year "+year);

        Lecturer lecturer=Lecturer.finder.byId(lec_id);
        List<Student> attendingStudents=new ArrayList<>();
        List<Cls> clsList=unit.getClasses();
        List<Cls> thisYearList=new ArrayList<>();
        for(Cls cl: clsList){
            if(cl.getDate().contains(year)&&cl.getLecturer().equals(lecturer)){
                thisYearList.add(cl);
                for(Attendance attendance:cl.attendanceList()){
                    Student student=attendance.getStudent();
                    if(!attendingStudents.contains(student)){
                        attendingStudents.add(student);
                    }
                }
            }
        }

        titles.add(attendingStudents.size()+ " Students Were In Attendance");

        for(Student student:attendingStudents){
            Map<String,String> data=new HashMap<>();
            data.put("name",student.fullName());
            data.put("reg_no",student.getReg_no());
            data.put("classes",thisYearList.size()+"");
            data.put("percentage",""+percentage(student,thisYearList)[1]);
            data.put("attended",""+percentage(student,thisYearList)[0]);
            atMaps.add(data);
        }


        return ok(views.html.pageViews.attendancePerUnitV.render(titles,sessionUser(request),atMaps,assetsFinder));
    }


    public static User sessionUser(Http.Request request){
        User user=User.finder.query().where().ieq("username",request.session().get("user").orElse("")).findOne();
        if(user==null){
            user=new User();
        }
        return  user;
    }



public static List<Unit> unitList(String email){
        List<Unit> units=new ArrayList<>();

        Lecturer lecturer=Lecturer.finder.query().where().ieq("email",email).findOne();
        units=lecturer.getUnits();
        if(units==null){
            units=new ArrayList<>();
        }

        return units;
}
public static Integer lec_id(String email){
    Lecturer lecturer=Lecturer.finder.query().where().ieq("email",email).findOne();
    return lecturer.getId();
}
 public static int[] percentage(Student st,List<Cls> clsList){
        int[] newInts=new int[2];
        List<Attendance> attendances=new ArrayList<>();
        for(Cls cl:clsList){
          Attendance attendance=Attendance.finder.query().where().eq("student",st).eq("cls",cl).findOne();
          if (attendance!=null&&!attendances.contains(attendance)){
              attendances.add(attendance);
          }

        }
        double ats=attendances.size();
        newInts[0]= (int) ats;
        double clsIze=clsList.size();

        double percent= (ats/clsIze)*100.0;
     newInts[1]= (int) percent;
        return newInts;
 }

}
