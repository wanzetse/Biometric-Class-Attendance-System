package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Unit  extends Model {
    @Id
    private int id;
    private String name;
    @OneToOne private Lecturer lecturer;

    public Unit(){

    }


}
