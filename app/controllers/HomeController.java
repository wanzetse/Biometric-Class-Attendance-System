package controllers;


import play.data.FormFactory;
import play.libs.Files;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.nio.file.Paths;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
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
    public Result index() {
        return ok(
            index.render(
                "Your new application is ready.",
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
