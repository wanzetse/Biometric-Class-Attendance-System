package models;

import com.machinezoo.sourceafis.FingerprintTemplate;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;


@Constraints.Validate
@Entity
public class Student extends Model implements Constraints.Validatable<List<ValidationError>>{


    @Id
    private Integer id;
    @Column(unique = true)
    @Constraints.Required
    @Constraints.MinLength(13)
    @Constraints.MaxLength(18)
    private String reg_no;
    @Constraints.Required
    @Constraints.MinLength(2)
    private String first_name;
    @Constraints.Required
    @Constraints.MinLength(2)
    private String sur_name;
    @Column(nullable = true)
    private  String last_name;

    @Constraints.Min(1)
    @Constraints.Max(4)
    private int year;

    @Constraints.Email
    @Constraints.Required
    private String email;
    @Constraints.Required
    private String phone;
    @ManyToOne
    @Column(nullable = true)
    private Course course;
    @Column(nullable = true)
    private String finger_id;
    private FingerprintTemplate fingerprintTemplate;



   public Student(){

   }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReg_no() {
        return reg_no.trim();
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no.trim();
    }

    public String getFirst_name() {
        return first_name.trim();
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name.trim();
    }

    public String getSur_name() {
        return sur_name.trim();
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name.trim();
    }

    public String getLast_name() {
        return last_name.trim().toUpperCase();
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getFinger_id() {
        return finger_id;
    }

    public void setFinger_id(String finger_id) {
        this.finger_id = finger_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public FingerprintTemplate getFingerprintTemplate() {
        return fingerprintTemplate;
    }

    public void setFingerprintTemplate(FingerprintTemplate fingerprintTemplate) {
        this.fingerprintTemplate = fingerprintTemplate;
    }

    public String fullName(){
        String fname= first_name != null ?first_name:"";
        String sname=sur_name != null ?sur_name:"";
        String lname=last_name!=null ?last_name:"";
        return fname+" "+sname+" "+lname;
    }

    public static Finder<Integer,Student> finder=new Finder<>(Student.class);

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors=new ArrayList<>();
        if(Lecturer.finder.query().where().ieq("email",email).exists()){
            errors.add(new ValidationError("email","Email Already Used"));
        }
        if(id==null){
            if(Student.finder.query().where().ieq("reg_no",reg_no).exists()){
                errors.add(new ValidationError("reg_no","Student With Registration Number Exists"));
            }
            if(Student.finder.query().where().ieq("email",email).exists()){
                errors.add(new ValidationError("email","Email Already Used"));
            }
        }else{
            Student student=finder.query().where().ieq("reg_no",reg_no).findOne();
            if(student!=null&&student.getId()!=id){
                errors.add(new ValidationError("reg_no","Registration Already Used"));
            }
            student=finder.query().where().ieq("email",email).findOne();
            if(student!=null&&student.getId()!=id){
                errors.add(new ValidationError("email","Email Already Used"));
            }
        }
        return errors;
    }
}
