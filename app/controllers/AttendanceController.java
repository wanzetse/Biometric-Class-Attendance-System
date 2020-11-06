package controllers;

import models.Attendance;
import models.Cls;
import models.Student;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AttendanceController extends Controller {
    private final FormFactory formFactory;
    private final  AssetsFinder assetsFinder;
    private final MessagesApi messagesApi;

    @Inject
    public AttendanceController(FormFactory formFactory, AssetsFinder assetsFinder, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.assetsFinder = assetsFinder;
        this.messagesApi = messagesApi;
    }

    public CompletionStage<Result> attendClass(Http.Request request){

        try{

            File file=request.body().asRaw().asFile();
            String filename="fingers/"+request.header("file-name").get();
            FileInputStream fileInputStream=new FileInputStream(file);
            String classId=request.header("class_id").get();
            Files.copy(fileInputStream, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
            Student student=Student.findByFingerPrint(Student.fingerprintTemplate(filename));
            if(student==null) {
                File file1=new File(filename);
                file1.delete();
                Result result=ok("Could Not Process Class Attendance\nFinger Not registered").withHeader("response","error");
                
                return  CompletableFuture.completedFuture(result);
            }
            Cls cls=Cls.finder.byId(Integer.parseInt(classId));
            if(cls==null){
                File file1=new File(filename);
                file1.delete();

                Result result=ok("Class Not Found").withHeader("response","error");
                return CompletableFuture.completedFuture(result);

            }
            List<Attendance> attStudents=cls.attendanceList();
            for(Attendance attendance:attStudents){
                if(student.equals(attendance.getStudent())){
                    Result result=ok("YOu Already Signed in").withHeader("response","error");
                    File file1=new File(filename);
                    file1.delete();

                    return CompletableFuture.completedFuture(result);
                }
            }

            Attendance attendance=new Attendance();
            attendance.setCls(cls);
            attendance.setStudent(student);
            attendance.save();



            Result result=ok("Welcome\n"+student.toString()).withHeader("response","success");
            return CompletableFuture.completedFuture(result);
        }catch(Exception e){
            e.printStackTrace();
            Result result=ok(e.getMessage()).withHeader("response","error");
            return CompletableFuture.completedFuture(result);
        }
    }

}
