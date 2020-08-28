package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Student extends Model {


    @Id
    private int id;
    private String reg_no;
    private String first_name;
    private String sur_name;
    @Column(nullable = true)
    private  String last_name;
    private int year;
    @ManyToOne
    private Course course;
    @Column(nullable = true)
    private String finger_id;



   public Student(){

   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
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

    public static Finder<Integer,Student> finder=new Finder<>(Student.class);
}
