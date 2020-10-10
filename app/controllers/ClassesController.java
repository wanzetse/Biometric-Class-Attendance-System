package controllers;

import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class ClassesController extends Controller {
    @Inject
    public ClassesController(){

    }
    public Result endClass(Integer lecd){
        return ok();
    }
}
