package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orderinfo")
public class Order implements Serializable {
    private int orderID;
    private String traineeID;
    private Date createTime;
    private boolean chooseClass;
    private int classNum;
    private String[] otherTrainee = new String[9];
    private double price;
    private int courseID;
    private String state;
    private String courseName;
    public Order() {

    }

    public Order(int orderID, String traineeID, Date createTime, boolean chooseClass, int classNum, String[] otherTrainee, double price, int courseID, String state) {
        this.orderID = orderID;
        this.traineeID = traineeID;
        this.createTime = createTime;
        this.chooseClass = chooseClass;
        this.classNum = classNum;
        this.otherTrainee = otherTrainee;
        this.price = price;
        this.courseID = courseID;
        this.state = state;
    }

    @Id
    @GeneratedValue
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Column(name = "traineeID")
    public String getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(String traineeID) {
        this.traineeID = traineeID;
    }

    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "chooseClass")
    public boolean isChooseClass() {
        return chooseClass;
    }

    public void setChooseClass(boolean chooseClass) {
        this.chooseClass = chooseClass;
    }

    @Column(name = "classNum")
    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    @Column(name = "otherTrainee")
    public String[] getOtherTrainee() {
        return otherTrainee;
    }

    public void setOtherTrainee(String[] otherTrainee) {
        this.otherTrainee = otherTrainee;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "courseID")
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Column(name = "courseName")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
