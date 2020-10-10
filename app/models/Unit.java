package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.testing.DummyStrings;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@Constraints.Validate
@Entity
public class Unit  extends Model implements Constraints.Validatable<List<ValidationError>> {
    @Id private Integer id;

    @Constraints.Required @Constraints.MinLength(4) @Column(unique = true) private String unitCode;

    @Constraints.Required @Constraints.MinLength(5) private String name;

    @JsonIgnore @Constraints.Required @ManyToOne private Lecturer lecturer;
    @OneToMany private List<Cls> classes;

    public Unit(){

    }

    public List<Cls> getClasses() {
        return classes;
    }

    public void setClasses(List<Cls> classes) {
        this.classes = classes;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public static Finder<Integer,Unit> finder=new Finder<>(Unit.class);

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors=new ArrayList<>();
        if(id==null){
            if(Unit.finder.query().where().ieq("unitCode",unitCode).exists()){
                errors.add(new ValidationError("unitCode",unitCode+" Already Exists"));
            }
        }
        if(id!=null){
            Unit unit=Unit.finder.query().where().ieq("unitCode",unitCode).findOne();
            if (unit.getId()!=id){

                errors.add(new ValidationError("unitCode",unitCode+" Already Used"));
            }
        }

        return errors;
    }

    public static Unit randomUnit(){
        Unit unit=new Unit();
        DummyStrings dms=new DummyStrings();
        unit.setUnitCode(dms.getUnitCode());
        unit.setName("");
        return unit;
    }

    public static LinkedHashMap<String,String> lecUnitOptions(Integer lec_id){
        LinkedHashMap<String,String> options=new LinkedHashMap<>();
       Lecturer lecturer=Lecturer.finder.byId(lec_id);
       for(Unit un:lecturer.getUnits()){
           options.put(""+un.id,"("+un.unitCode+") "+un.name);
       }
       return options;
    }
}
