package controllers;

import models.Lecturer;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AdminController extends Controller {
    private final FormFactory formFactory;
    private final AssetsFinder assetsFinder;
    private final MessagesApi messagesApi;

    @Inject
    public AdminController(FormFactory formFactory, AssetsFinder assetsFinder, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.assetsFinder = assetsFinder;
        this.messagesApi = messagesApi;
    }

    public Result lecturerList(Http.Request request){
        return ok("");
    }




}

