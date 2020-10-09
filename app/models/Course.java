package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Constraints.Validate
@Entity
public class Course extends Model implements Constraints.Validatable<List<ValidationError>> {

    @Id private Integer id;
    @Constraints.Required
    @Constraints.MinLength(2)
    @Column(unique = true)
    private String courseCode;
    @Constraints.Required
    @Constraints.MinLength(2)
    private String name;

public Course(){

}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public static Finder<Integer,Course> finder=new Finder<>(Course.class);

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors=new ArrayList<>();
        if(id==null){
            if(finder.query().where().ieq("courseCode",courseCode).exists()){
                errors.add(new ValidationError("courseCode","Course Exists"));
            }
        }else{
            Course course=finder.query().where().ieq("courseCode",courseCode).findOne();
            if(course!=null&&course.getId()!=id){

                errors.add(new ValidationError("courseCode","Course Exists"));

            }
        }

        return errors;
    }

    public static Map<String,String> options(){
        LinkedHashMap<String,String> options=new LinkedHashMap<>();
        for(Course cs:finder.all()){
            options.put(""+ cs.id,cs.name);
        }
        return  options;
    }

    public static Course randomCourse(){
        Course course=finder.query().findOne();
        if(course==null){
            course=new Course();
            course.setName("Bsc Computer Science");
            course.setCourseCode("Bsc Cs");
            course.save();
        }

        return course;
    }
}
