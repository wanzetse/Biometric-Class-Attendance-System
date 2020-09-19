package controllers;


import controllers.Auth.Secured;
import models.User;
import play.data.FormFactory;
import play.libs.Files;
import play.mvc.*;
import views.html.*;
import javax.inject.Inject;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Security.Authenticated(Secured.class)
public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;
    private final  FormFactory formFactory;

    @Inject
    public HomeController(AssetsFinder assetsFinder,FormFactory formFactory) {
        this.assetsFinder = assetsFinder;
        this.formFactory=formFactory;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        List<String> titles=new ArrayList<>();
        titles.add("Laikipia University Portal");
        titles.add("Lectures");
        titles.add("List Of All Lectures");
        User user=User.finder.query().where().ieq("username",request.session().get("user").orElse("")).findOne();
        if(user==null){
            user=new User();
        }
        return ok(
            index.render(
                titles,user,
                assetsFinder
            ));
    }

    public Result process_file(Http.Request request){
        Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<Files.TemporaryFile> finger_print = body.getFile("file");
        if(finger_print!=null){
            String fileName = finger_print.getFilename();
            Files.TemporaryFile file = finger_print.getRef();
            file.copyTo(Paths.get("fingers\\"+fileName),true);
            return  ok("Everything went successful");
        }

        return ok("We could Not Process Finger");
    }

}
