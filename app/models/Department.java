package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Department extends Model {
    @Id
    private Integer id;
    @Column(unique = true)
    private String name;
    @Nullable
    @Column(nullable = true)
    private Lecturer departmentHead;

    public Department(){

    }

    public Department(Integer id, String name, @Nullable Lecturer departmentHead) {
        this.id = id;
        this.name = name;
        this.departmentHead = departmentHead;
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

    @Nullable
    public Lecturer getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(@Nullable Lecturer departmentHead) {
        this.departmentHead = departmentHead;
    }
    public  static Finder<Integer,Department> finder=new Finder<>(Department.class);

    public static Map<String,String > options(){
        LinkedHashMap<String,String> options=new LinkedHashMap<>();
        for(Department dep:Department.finder.all()){
            options.put(dep.getId()+"",dep.getName());
        }
        return options;
    }


}
