package models;

import io.ebean.Model;

import javax.management.MalformedObjectNameException;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Lecturer extends Model {
    @Id
    private int id;

    private String work_id;
    private String first_name;
    private String sur_name;
    private String last_name;
    @OneToMany private List<Unit> units;
    @OneToMany private List<Cls> clsList;

    public Lecturer(int id, String work_id, String first_name, String sur_name, String last_name) {
        this.id = id;
        this.work_id = work_id;
        this.first_name = first_name;
        this.sur_name = sur_name;
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Cls> getClsList() {
        return clsList;
    }

    public void setClsList(List<Cls> clsList) {
        this.clsList = clsList;
    }
}
