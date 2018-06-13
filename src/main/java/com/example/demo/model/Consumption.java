package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consumption")
public class Consumption {
    private int id;
    private String traineeID;
    private double price;
    private String reason;
    private Date time;
    private int orderID;
    private boolean isSettled;

    public Consumption(){
        this.isSettled = false;
    }
    public Consumption( String traineeID, double price, String reason, Date time, int orderID) {
        this.traineeID = traineeID;
        this.price = price;
        this.reason = reason;
        this.time = time;
        this.orderID = orderID;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "traineeID")
    public String getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(String traineeID) {
        this.traineeID = traineeID;
    }
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Column(name="reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "orderID")
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Column(name = "isSettled")
    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }
}
