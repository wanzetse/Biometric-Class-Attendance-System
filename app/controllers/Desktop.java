package controllers;


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


        response.put("response","success");
        response.put("message","Login Success");
        response.put("user",""+user.getId());
        return ok(Json.toJson(response));
    }

    public Result fingerR(Http.Request request){
      File file=request.body().asRaw().asFile();
      try {
          String filename=request.header("file-name").get();
          FileInputStream fileInputStream=new FileInputStream(file);
          Files.copy(fileInputStream, Paths.get("fingers\\"+filename), StandardCopyOption.REPLACE_EXISTING);

      }catch (Exception e){
          e.printStackTrace();
      }


        return ok("Tried to save a file");
    }

}
