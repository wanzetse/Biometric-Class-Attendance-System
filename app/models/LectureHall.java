package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import java.util.List;
@Entity
public class LectureHall extends Model {
    @Id
    private  int id;
    private String name;
    public LectureHall(){
        
    }
}
