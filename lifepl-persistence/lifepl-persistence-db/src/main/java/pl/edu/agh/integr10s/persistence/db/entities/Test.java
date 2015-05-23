package pl.edu.agh.integr10s.persistence.db.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "test")
public class Test {

    @Id
    @GenericGenerator(name="test_gen" , strategy="increment")
    @GeneratedValue(generator="test_gen")
    private int id;

    private String data;


    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return data + "[" + id + "]";
    }
}