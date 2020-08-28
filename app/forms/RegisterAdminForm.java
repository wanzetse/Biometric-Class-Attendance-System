package forms;


import models.User;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

@Constraints.Validate
public class RegisterAdminForm implements Constraints.Validatable<List<ValidationError>>{
    @Constraints.MinLength(5) @Constraints.MaxLength(20) private String username;

    @Constraints.Email private String email;
    @Constraints.MinLength(6) private String password;
    @Constraints.MinLength(6) private String password2;

    public RegisterAdminForm(){

    }

    public RegisterAdminForm(@Constraints.MinLength(5) @Constraints.MaxLength(20) String username,
                             @Constraints.Email String email,
                             @Constraints.MinLength(6) String password,
                             @Constraints.MinLength(6) String password2) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;
    }

    @Override
    public List<ValidationError> validate() {
        final List<ValidationError> errors = new ArrayList<>();
        if(User.finder.query().where().ieq("username",this.username).exists()){
            errors.add(new ValidationError("username","Username Already Exists"));
        }
        if(User.finder.query().where().ieq("email",email).exists()){
            errors.add(new ValidationError("email","Email Already Exists"));
        }
        if(!password.equals(password2)){
            errors.add(new ValidationError("password2","Passwords Do Not Match"));
        }
        if(errors.size()>0){
            errors.add(new ValidationError("","Failed Please Correct Errors Below"));
        }
        return  errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
