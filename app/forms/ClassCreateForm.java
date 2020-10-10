package forms;

import models.Cls;
import models.LectureHall;
import models.Lecturer;
import models.Unit;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

@Constraints.Validate
public class ClassCreateForm implements Constraints.Validatable<List<ValidationError>>{
    @Constraints.Required
    private Lecturer lecturer;
    @Constraints.Required
    private LectureHall lectureHall;
    @Constraints.Required
    private Unit unit;
    @Constraints.Required
    @Constraints.Max(3)
    private double duration;


    public ClassCreateForm(){

    }

    public ClassCreateForm(@Constraints.Required Lecturer lecturer,
                           @Constraints.Required LectureHall lectureHall,
                           @Constraints.Required Unit unit,
                           @Constraints.Required @Constraints.Max(3) double duration) {
        this.lecturer = lecturer;
        this.lectureHall = lectureHall;
        this.unit = unit;
        this.duration = duration;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors=new ArrayList<>();
        for(Cls cl: Cls.finder.all()){
            if(cl.isOngoing()){
                if(cl.getLectureHall().equals(lecturer)) {
                    errors.add(new ValidationError("duration", "You got An Ongoing Class Now"));
                }
                if(cl.getLectureHall().equals(lectureHall)){
                    errors.add(new ValidationError("lectureHall","Hall Is Currently In Use"));
                }

            }
        }

        return errors;
    }
}
