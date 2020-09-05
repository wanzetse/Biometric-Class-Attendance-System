package models;

import io.ebean.Finder;
import io.ebean.Model;
import modules.HashP;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends Model  {
    @Id
    private int id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;

    private String password;
    private boolean student;
    private boolean lecturer;
    private boolean admin;

    public User(){

    }

    public User(int id, String username, @Constraints.Email String email, String password,
                boolean student, boolean lecturer, boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.student = student;
        this.lecturer = lecturer;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.password = HashP.haspP(password);
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public boolean isLecturer() {
        return lecturer;
    }

    public void setLecturer(boolean lecturer) {
        this.lecturer = lecturer;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public static Finder<Integer,User> finder=new Finder<>(User.class);

}
