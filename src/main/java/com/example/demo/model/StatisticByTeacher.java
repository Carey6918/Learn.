package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "statisticByTeacher")
public class StatisticByTeacher {
    private int institutionID;
    private String teacher;
    private Date time;
    private double cancelPercent;
    private int id;
    private double goodPercent;
    private double failPercent;
    public StatisticByTeacher(){}
    public StatisticByTeacher(int institutionID, String teacher, Date time, double cancelPercent) {
        this.institutionID = institutionID;
        this.teacher = teacher;
        this.time = time;
        this.cancelPercent = cancelPercent;
    }

    @Column(name = "institutionID")
    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
    @Column(name = "teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    @Column(name = "cancelPercent")
    public double getCancelPercent() {
        return cancelPercent;
    }

    public void setCancelPercent(double cancelPercent) {
        this.cancelPercent = cancelPercent;
    }
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "failPercent")
    public double getFailPercent() {
        return failPercent;
    }

    public void setFailPercent(double failPercent) {
        this.failPercent = failPercent;
    }

    @Column(name = "goodPercent")
    public double getGoodPercent() {
        return goodPercent;
    }

    public void setGoodPercent(double goodPercent) {
        this.goodPercent = goodPercent;
    }
}
