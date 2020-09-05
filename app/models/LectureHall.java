package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class LectureHall extends Model {
    @Id
    private  Integer id;
    @Column(unique = true)
    @Constraints.Required
    @Constraints.MinLength(3)
    private String room_name;
    private String block_name;
    private Integer capacity;
    public LectureHall(){
        
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public static Finder<Integer,LectureHall> finder =new Finder<>(LectureHall.class);
}
