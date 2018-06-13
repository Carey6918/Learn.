package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "trainee")
public class Trainee implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private int VIPlevel;
    private String sex;
    private int age;
    private String name;
    private int points;
    private int consumption;

    public Trainee() {
    }

    public Trainee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "VIPlevel")
    public int getVIPlevel() {
        return VIPlevel;
    }

    public void setVIPlevel(int VIPlevel) {
        this.VIPlevel = VIPlevel;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Column(name = "consumption")
    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }
}
