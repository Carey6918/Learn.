package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class StatisticByCourse {
    private int id;
    private int institutionID;
    private int volume;
    private int profit;

    @Column(name = "volume")
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Column(name = "profit")
    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "institutionID")
    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }
}
