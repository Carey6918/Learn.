package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "institution")
public class Institution implements Serializable {
    private int institutionID;
    private String password;
    private String name;
    private String location;
    private String teacher;
    private String introduction;


    public Institution() {
    }

    public Institution(int institutionID, String password, String name, String location, String teacher, String introduction) {
        this.institutionID = institutionID;
        this.password = password;
        this.name = name;
        this.location = location;
        this.teacher = teacher;
        this.introduction = introduction;
    }

    @Id
    @GeneratedValue
    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Column(name = "introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


}
