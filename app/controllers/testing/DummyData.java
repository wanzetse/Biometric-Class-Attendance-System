package controllers.testing;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
   private  List<Student> studentList;
   private List<Course> courses;
   private List<LectureHall> lectureHalls;
   private List<Unit> units;
   private List<Department> departments;
   private List<User> users;
   private List<Cls> classes;

    public DummyData() {
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<LectureHall> getLectureHalls() {
        return lectureHalls;
    }

    public void setLectureHalls(List<LectureHall> lectureHalls) {
        this.lectureHalls = lectureHalls;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Cls> getClasses() {
        return classes;
    }

    public void setClasses(List<Cls> classes) {
        this.classes = classes;
    }
}
