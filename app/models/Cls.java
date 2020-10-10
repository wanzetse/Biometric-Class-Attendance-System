package models;

import io.ebean.Finder;
import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "classes")

public class Cls extends Model {

    @Id
    private Integer id;
    @ManyToOne private Unit unit;
    private String date;
    @ManyToOne private Lecturer lecturer;
    @ManyToOne private LectureHall lectureHall;
    private double duration;
    private String start_time;
    private String end_time;

    @OneToMany private List<Attendance> attendances;



    public Cls(){

    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Integer getId() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
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

    public boolean isOngoing(){
        try {

            DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
            String date1 = dateFormat.format(new Date());
            if(!date.equals(date1)){
                return  false;
            }
            Date today=new Date();
            DateFormat dateFormat1=new SimpleDateFormat("HH:mm:ss");
            String timenow=dateFormat1.format(today);
            if(dateFormat1.parse(timenow).before(dateFormat1.parse(end_time))){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public List<Attendance> attendanceList(){
        List<Attendance> attendances=new ArrayList<>();
        for(Attendance attendance:Attendance.finder.all()){
            if (attendance.getCls().equals(this)){
                attendances.add(attendance);
            }
        }
        return attendances;
    }

    public static Finder<Integer,Cls> finder=new Finder<>(Cls.class);
}
