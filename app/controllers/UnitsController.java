package controllers;

import models.Unit;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitsController extends Controller {
    private final FormFactory formFactory;
    private final AssetsFinder assetsFinder;
    private final MessagesApi messagesApi;

    @Inject
    public UnitsController(FormFactory formFactory, AssetsFinder assetsFinder, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.assetsFinder = assetsFinder;
        this.messagesApi = messagesApi;
    }

    public Result adminIndex(Http.Request request){
        List<Unit> unitList=Unit.finder.all();
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Units");
        titles.add("Units");
        titles.add(unitList.size()+" Registered Units");
        User user=LecturersController.sessionUser(request);
      return ok(views.html.pageViews.unitlistview.render(titles,user,unitList,assetsFinder));
    }

    public Result add_EditUnit(Http.Request request){
        Form<Unit> unitForm=formFactory.form(Unit.class);
        List<String> titles=new ArrayList<>();
        User user=LecturersController.sessionUser(request);
        titles.add("Admin | Laikipia University | Add / Edit Unit");
        titles.add("Add / Edit Unit");
        titles.add("Unit Form Details");
        return ok(views.html.formsviews.unitsform.render(titles,unitForm,user,assetsFinder,messagesApi.preferred(request),request));

    }
    public Result add_EditUnitP(Http.Request request){
        User user=LecturersController.sessionUser(request);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Add / Edit Unit");
        titles.add("Add / Edit Unit");
        titles.add("Unit Form Details");
        Form<Unit> unitForm=formFactory.form(Unit.class).bindFromRequest(request);
        System.out.println(unitForm);
        System.out.println("\n\n"+unitForm.errors()+"\n\n");
        if(unitForm.hasErrors()){
            return badRequest(views.html.formsviews.unitsform.render(titles,unitForm,user,assetsFinder,messagesApi.preferred(request),request));
        }

        Unit unit=unitForm.get();

        if(unitForm.field("id").value().get().isEmpty()){
            unit.save();
        }else {
            unit.update();
        }
        Map<String,String> flash=new HashMap<>();
        flash.put("success","Unit Saved Successfully");
        return redirect(routes.UnitsController.add_EditUnit()).withFlash(flash);

    }

    public Result editUnit(Http.Request request,String unitCode){
        User user=LecturersController.sessionUser(request);
        List<String> titles=new ArrayList<>();
        titles.add("Admin | Laikipia University | Add / Edit Unit");
        titles.add("Add / Edit Unit");

        Form<Unit> unitForm=formFactory.form(Unit.class);
        Unit unit=Unit.finder.query().where().ieq("unitCode",unitCode).findOne();
        if(unit==null){
            return redirect(routes.UnitsController.add_EditUnit());
        }
        unitForm=unitForm.fill(unit);
        titles.add("Edit "+unitCode);


        return ok(views.html.formsviews.unitsform.render(titles,unitForm,user,assetsFinder,
                messagesApi.preferred(request),request));
    }
}
