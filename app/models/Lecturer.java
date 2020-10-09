package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.testing.DummyStrings;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.management.MalformedObjectNameException;
import javax.persistence.*;
import java.util.*;

@Constraints.Validate
@Entity
public class Lecturer extends Model implements Constraints.Validatable<List<ValidationError>>{
    @Id private Integer id;

    @Constraints.Required @Constraints.MinLength(4) @Column(unique = true) private String work_id;

    @Constraints.Required private String first_name;

    @Constraints.Required private String sur_name;

    private String last_name;

    @Constraints.Email @Constraints.Required @Column(unique = true) private String email;

    private boolean departmentHead;

    @JsonIgnore  @ManyToOne private Department department;

    @Constraints.MinLength(10) @Constraints.MaxLength(14) private String phone;

    @Constraints.Required private String title;

    @JsonIgnore  @OneToMany private List<Unit> units;

    @JsonIgnore @OneToMany private List<Cls> clsList;

    public Lecturer() {
    }

    public Lecturer(Integer id, @Constraints.Required @Constraints.MinLength(4) String work_id,
                    String first_name, String sur_name, String last_name, @Constraints.Email String email,
                    boolean departmentHead, Department department,
                    @Constraints.MinLength(10) @Constraints.MaxLength(14) String phone,
                    @Constraints.Required String title, List<Unit> units, List<Cls> clsList) {
        this.id = id;
        this.work_id = work_id;
        this.first_name = first_name;
        this.sur_name = sur_name;
        this.last_name = last_name;
        this.email = email;
        this.departmentHead = departmentHead;
        this.department = department;
        this.phone = phone;
        this.title = title;
        this.units = units;
        this.clsList = clsList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(boolean departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public int getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String fullName(){
        String lasname=last_name!=null ?last_name:"";
        String surname=sur_name != null ?sur_name:"";
        String fname= first_name != null ?first_name:"";
        return fname+" "+surname+" "+lasname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Cls> getClsList() {
        return clsList;
    }

    public void setClsList(List<Cls> clsList) {
        this.clsList = clsList;
    }

    public static Finder<Integer,Lecturer> finder=new Finder<>(Lecturer.class);

    public static Map<String,String> options(){
        LinkedHashMap<String,String> options = new LinkedHashMap<>();

        // Get all categories from the DB and add to the options hash map
        for (Lecturer c: Lecturer.finder.all()) {
            options.put(c.getId()+"", c.fullName()+" ("+c.getWork_id()+")");
        }
        return options;
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors=new ArrayList<>();

        if(id==null){
            if(Lecturer.finder.query().where().ieq("work_id",work_id).exists()){
                errors.add(new ValidationError("work_id","Work Id Already Exists"));
            }
            if(Lecturer.finder.query().where().ieq("email",email).exists()){
                errors.add(new ValidationError("email","Email Already Used"));
            }
        }else{
           Lecturer lecturer= Lecturer.finder.query().where().ieq("work_id",work_id).findOne();
            if(lecturer.getId()!=id){

                errors.add(new ValidationError("work_id","Work Id Already Exists"));
            }
            lecturer=Lecturer.finder.query().where().ieq("email",email).findOne();
            if(lecturer.getId()!=id){
                errors.add(new ValidationError("email","Email Already Used"));
            }
        }

        return errors;
    }

    public static Map<String,String> titleOptions(){
       LinkedHashMap<String,String> options=new LinkedHashMap<>();
        options.put("Mr","Mr");
        options.put("Mrs","Mrs");
        options.put("Ms","Ms");
        options.put("Dr","Dr");
        options.put("Prof","Prof");


        return options;
    }

    public static Lecturer randomLecturer(){
        DummyStrings dms=new DummyStrings();
        Lecturer lecturer=new Lecturer();
        lecturer.setFirst_name(dms.getName());
        lecturer.setSur_name(dms.getName());
        lecturer.setLast_name(dms.getName());
        String[] names={lecturer.getFirst_name(),lecturer.getSur_name(),lecturer.getLast_name()};
        lecturer.setEmail(dms.getEmail(names));
        lecturer.setWork_id(dms.getWorkId());
        lecturer.setPhone(dms.getPhone());
        lecturer.setTitle(dms.getTitle());
        return lecturer;
    }

}
