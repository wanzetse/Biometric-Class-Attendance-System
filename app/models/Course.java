package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course extends Model {

    @Id private int id;
    private String name;

public Course(){

}
}
