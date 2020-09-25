package models;

import java.util.List;
import java.util.Random;
import io.ebean.Finder;
import io.ebean.Model;
import org.hibernate.validator.constraints.CodePointLength;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Attendance extends Model {
    @Id
    private Integer id;
    @ManyToOne
    private Cls cls;
    @ManyToOne
    private Student student;

    public Attendance() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cls getCls() {
        return cls;
    }

    public void setCls(Cls cls) {
        this.cls = cls;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    //Random test data for Attendance
    public static Attendance randomAttendance(){
        Attendance attendance=new Attendance();
        List<Cls> classes=Cls.finder.all();
        List<Student> studentList=Student.finder.all();

        return attendance;
    }
    public static Finder<Integer, Attendance> finder=new Finder<>(Attendance.class);
}
