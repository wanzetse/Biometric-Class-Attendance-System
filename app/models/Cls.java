package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "classes")

public class Cls extends Model {

    @Id
    private Integer id;
    @ManyToOne
    private Unit unit;
    private Date date;
    @ManyToOne
    private Lecturer lecturer;
    @ManyToOne
    private LectureHall lectureHall;
    private String duration;
    private String start_time;
    private String end_time;

    public Cls(Integer id, Unit unit, Date date,
               Lecturer lecturer, LectureHall lectureHall,
               String duration, String start_time, String end_time) {
        this.id = id;
        this.unit = unit;
        this.date = date;
        this.lecturer = lecturer;
        this.lectureHall = lectureHall;
        this.duration = duration;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public Cls(){

    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public LectureHall getLectureHall() {
        return lectureHall;
    }

    public void setLectureHall(LectureHall lectureHall) {
        this.lectureHall = lectureHall;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public static Finder<Integer,Cls> finder=new Finder<>(Cls.class);
}
