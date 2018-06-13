package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "course")
public class Course implements Serializable{
    private int courseID;
    private int institutionID;
    private Date startTime;
    private Date endTime;
    private double period;
    private int times;
    private double price;
    private String teacher;
    private int traineeNums;
    private String name;
    private String type;
    private String introduction;
    private String institutionName;

    public Course(){

    }
    public Course(int institutionID,Date startTime,Date endTime,double period,
    int times,double price,String teacher,int traineeNums,String name, String type,String introduction){
        this.institutionID = institutionID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
        this.teacher = teacher;
        this.times = times;
        this.price = price;
        this.traineeNums = traineeNums;
        this.name = name;
        this.type = type;
        this.introduction = introduction;
    }
    @Id
    @GeneratedValue
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Column(name="institutionID")
    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
    @Column(name="startTime")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Column(name="endTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    @Column(name="times")
    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
    @Column(name="price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Column(name="teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    @Column(name="traineeNums")
    public int getTraineeNums() {
        return traineeNums;
    }

    public void setTraineeNums(int traineeNums) {
        this.traineeNums = traineeNums;
    }
    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name="introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    @Column(name="period")
    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    @Column(name="institutionName")
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
}
