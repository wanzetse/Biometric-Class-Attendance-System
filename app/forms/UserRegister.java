package forms;

import models.Lecturer;
import models.Student;
import models.User;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
@Constraints.Validate
public class UserRegister implements Constraints.Validatable<List<ValidationError>>{
    @Constraints.Email private String email;

    @Constraints.MinLength(2) private String user_id;

    private String destination;

    @Constraints.MinLength(5) private String username;

    @Constraints.MinLength(6) private String password;

    @Constraints.MinLength(6) private String password2;


    public UserRegister(){
        
    }
    public UserRegister(@Constraints.Email String email, @Constraints.MinLength(2) String user_id,
                        String destination, @Constraints.MinLength(5) String username,
                        @Constraints.MinLength(6) String password,
                        @Constraints.MinLength(6) String password2) {
        this.email = email;
        this.user_id = user_id;
        this.destination = destination;
        this.username = username;
        this.password = password;
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors=new ArrayList<>();
        if(!password.equals(password2)){
            errors.add(new ValidationError("password2","Passwords Do Not Match"));
        }
        if(destination.equals("student")){
            Student student=Student.finder.query().where().ieq("email",email).findOne();
            if (student==null){
                errors.add(new ValidationError("email","No Such Student Email"));
            }
            Student student1=Student.finder.query().where().ieq("reg_no",user_id).findOne();
            if(student1==null){
                errors.add(new ValidationError("user_id","No Such Student Reg number"));

            }
        }
        if(destination.equals("staff")){
            Lecturer lecturer=Lecturer.finder.query().where().ieq("email",email).findOne();
            if(lecturer==null){
                errors.add(new ValidationError("email","No Such Staff email"));
            }
            Lecturer lecturer1=Lecturer.finder.query().where().ieq("work_id",user_id).findOne();
            if(lecturer1==null){
                errors.add(new ValidationError("user_id","No Such Staff Id"));
            }
        }


        User user= User.finder.query().where().ieq("username",username).findOne();
        User user1=User.finder.query().where().ieq("email",email).findOne();
        if(user!=null){
            errors.add(new ValidationError("username","Username Already Used"));
        }
        if(user1!=null){
            errors.add(new ValidationError("email","Email Already Used"));
        }
        return errors;
    }
//    public Li
}
