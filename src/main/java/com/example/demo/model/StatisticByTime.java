package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "StatisticByTime")
public class StatisticByTime {

    //private Date date;
    private int type;//1表示管理员，2表示机构，3表示用户
    //private String id;
    private int volume;
    private double profit;
    private StatisticKey key;

    public StatisticByTime(Date date, int type,String id, int volume, double profit){
        this.key.date =date;
        this.type = type;
        this.key.id = id;
        this.volume = volume;
        this.profit = profit;
    }

    public Date getDate() {
        return key.date;
    }

    public void setDate(Date date) {
        this.key.date = date;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getId() {
        return key.id;
    }

    public void setId(String id) {
        this.key.id = id;
    }

    @Column(name = "volume")
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Column(name = "profit")
    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @EmbeddedId
    public StatisticKey getKey(){
        return this.key;
    }
}