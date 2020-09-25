package controllers;


import controllers.testing.DummyStrings;
import models.Student;
import models.User;
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
import java.util.HashMap;
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
            response.put("response","error");
            response.put("message","Authentication failure\n Wrong username or Password");
            return ok(Json.toJson(response));
        }
        if(!HashP.checkP(password,user.getPassword())){
            response.put("response","error");
            response.put("message","Authentication failure\n Wrong username or Password");
            return ok(Json.toJson(response));
        }
        if(user.isStudent()){
            response.put("response","error");
            response.put("message","Unauthorised");
            return ok(Json.toJson(response));
        }

        String user_type=user.isAdmin()?"admin":"lecturer";
        response.put("response","success");
        response.put("message","Login Success");
        response.put("user",""+user.getId());
        return ok(Json.toJson(response)).withHeader("user",user.getId()+"").withHeader("user-type",user_type);
    }

    public Result fingerR(Http.Request request){
        Map<String, String> response=new HashMap<>();
      try {
          File file=request.body().asRaw().asFile();
          String filename=request.header("file-name").get();
          String student_reg=request.header("student").get();
          int user_id=Integer.parseInt(request.header("user").get());
          User user=User.finder.byId(user_id);
          if(user.isAdmin()){
              Student student =Student.finder.query().where().ieq("reg_no",student_reg).findOne();
              FileInputStream fileInputStream=new FileInputStream(file);
              Files.copy(fileInputStream, Paths.get("fingers\\"+filename), StandardCopyOption.REPLACE_EXISTING);
              student.setFinger_id(filename);
              student.update();
              response.put("response","success");
              response.put("message","Student finger Print Saved Successfully");
              return ok(Json.toJson(response));
          }
          response.put("response","error");
          response.put("message","System Could Not Authenticate if you could do that");

      }catch (Exception e){
          e.printStackTrace();
          response.put("response","error");
          response.put("message",e.getMessage());
          return ok(Json.toJson(response)).withHeader("error",e.getMessage());


      }


        return ok("Tried to save a file");
    }

    public CompletionStage<Result> noFingerPrintStudents(){

        return CompletableFuture.completedFuture(ok(""));

    }
     public CompletionStage<Result> fingerPrintStudents(){

        return CompletableFuture.completedFuture(ok(""));

    }

   public CompletionStage<Result> randomData(){
       DummyStrings dms=new DummyStrings();
       StringBuilder user=new StringBuilder();
       for(int i =0;i<10000;i++){
           user.append("<p>"+i+" "+dms.getName()+": "+dms.getPhone()+"</p>\n\n");
       }
        return CompletableFuture.completedFuture(ok(user.toString()).as("text/html"));
   }

}
