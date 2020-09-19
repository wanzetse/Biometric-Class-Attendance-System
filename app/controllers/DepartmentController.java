package controllers;

import models.Department;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DepartmentController extends Controller {
    private final FormFactory formFactory;
    private final AssetsFinder assetsFinder;
    private final MessagesApi messagesApi;

    @Inject
    public DepartmentController(FormFactory formFactory, AssetsFinder assetsFinder, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.assetsFinder = assetsFinder;
        this.messagesApi = messagesApi;
    }

    public Result adminIndex(Http.Request request){
        return ok("");
    }

    public Result add_EditDepartment(Http.Request request) {
        Form<Department> departmentForm=formFactory.form(Department.class);
        List<String> titles=new ArrayList<>();
        titles.add("Laikipia University| Admin| Add/Edit Department");
        titles.add("Add Department");
        titles.add("Department Form Details");
        User user=LecturersController.sessionUser(request);
        Content content=views.html.formsviews.deptform.render(titles,departmentForm,user,
                assetsFinder,messagesApi.preferred(request),request);
        return ok(content);
    }

    public Result add_EditDeptP(Http.Request request){
        return ok("");
    }

    public Result editDept(Http.Request request,String courseId){
        return ok("");
    }
}
