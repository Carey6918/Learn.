package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="institutionApplication")
public class InstitutionApplication implements Serializable{
    private int applicationID;
    private String password;
    private String name;
    private String location;
    private String teacher;
    private String introduction;
    private String type;//修改还是增加
    private String reason;

    public InstitutionApplication(int applicationID, String password, String name, String location, String teacher, String introduction, String type,String reason) {
        this.applicationID = applicationID;
        this.password = password;
        this.name = name;
        this.location = location;
        this.teacher = teacher;
        this.introduction = introduction;
        this.type = type;
        this.reason = reason;
    }
    public InstitutionApplication() {
    }

    @Id
//    @GeneratedValue
//    @TableGenerator(name = "7uid",table = "tb_generator",pkColumnName="gen_name",
//            valueColumnName="gen_value",
//            pkColumnValue="7uid_PK", initialValue = 1000000)
    public int getApplicationID() {
        return applicationID;
    }


    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }
    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Column(name="teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    @Column(name="introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    @Column(name="type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
