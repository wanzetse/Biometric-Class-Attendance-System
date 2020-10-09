package controllers;

import models.*;
import modules.HashP;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
        Map<String,String> response=new HashMap<>();
        User user= User.finder.query().where().ieq("username",username).findOne();
        if(user==null){
            return ok("Authentication failure\n Wrong username or Password").withHeader("response","error");
        }
       else  if(!HashP.checkP(password,user.getPassword())){

            return ok("Authentication failure\n Wrong username or Password").withHeader("response","error");

        }
       else if(user.isStudent()){
            response.put("response","error");
            response.put("message","Unauthorised");
            return ok("Authentication failure\n Wrong username or Password")
                    .withHeader("response","error");
        }
        else if(user.isLecturer()){
            Lecturer lecturer=Lecturer.finder.query().where().eq("email",user.getEmail()).findOne();
            return ok("Login Success").withHeader("user-type","lecturer")
                    .withHeader("lec_id",""+lecturer.getId())
                    .withHeader("response","success");
        }
        else{
            return  ok("Login Success").withHeader("response","success")
                    .withHeader("user-type","admin")
                    ;
        }
 }

    public Result fingerR(Http.Request request){

      try {
          File file=request.body().asRaw().asFile();
          String filename=request.header("file-name").get();
          String student_reg=request.header("student").get();


              Student student =Student.finder.query().where().ieq("reg_no",student_reg).findOne();
              FileInputStream fileInputStream=new FileInputStream(file);
              Files.copy(fileInputStream, Paths.get("fingers\\"+filename), StandardCopyOption.REPLACE_EXISTING);
              if(Student.findByFingerPrint(
                      Student.fingerprintTemplate("fingers\\"+filename))==null){
                  student.setFinger_id(filename);
                  student.update();
                  return ok("Student Finger Print SaveD Successfully").withHeader("response","success");

              }else {
                  File file1=new File("fingers\\"+filename);
                  file1.delete();
                  return ok("Finger Print Already Registered").withHeader("response","error");

              }





      }catch (Exception e){
          e.printStackTrace();

          return ok(e.getMessage()).withHeader("response","error");


      }


    }

    public CompletionStage<Result> noFingerPrintStudents(){

        return CompletableFuture.completedFuture(ok(""));

    }
     public CompletionStage<Result> fingerPrintStudents(){

        return CompletableFuture.completedFuture(ok(""));

    }

   public CompletionStage<Result> randomData(){

       System.out.println("");
       List<Student> students=new ArrayList<>();
       int i = 0;
       for(;;){
           Student student=Student.randomStudent();
           if(student!=null){
               students.add(student);
               i+=1;
               System.out.println(i+"\t"+student);
           }
           if(i>=100){
               break;
           }


       }

        return CompletableFuture.completedFuture(ok(Json.toJson(students)));
   }

   public CompletionStage<Result> tobeRegisteredStudents(Http.Request request){


        StringBuilder students=new StringBuilder();
        List<Student> studentList=new ArrayList<>();
        try {
            String exp=request.header("exp").get();
            String q=request.header("q").get();
            System.out.println(exp);
            if (q.equals("registered")) {
                studentList = Student.finder.query().where().isNotNull("finger_id").findList();
            } else if (q.equals("non-registered")) {
                studentList = Student.finder.query().where().isNull("finger_id")
                        .findList();
            }
            for (Student student : studentList) {
                if (student.toString().toLowerCase().contains(exp.toLowerCase())) {
                    students.append(student.getReg_no() + "\t" + student.fullName() + "\n");
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return CompletableFuture.completedFuture(ok(students.toString()));
   }


}
